package com.eriks.core

import com.eriks.core.config.CardGenerator
import com.eriks.core.objects.*
import com.eriks.core.repository.CardRepository
import com.eriks.core.repository.PackageRepository
import com.eriks.core.repository.ParamRepository
import com.eriks.core.ui.util.UIUtil
import com.eriks.core.util.ExternalUtil
import com.eriks.core.util.LoggerConfig
import java.time.Instant
import java.util.*

object GameController {

    const val VERSION = "1.2.0"
    var isVersionValid = false

    private val logger = LoggerConfig.getLogger()

    lateinit var packageRepository: PackageRepository
    lateinit var paramRepository: ParamRepository
    lateinit var cardRepository: CardRepository

    private var params = mapOf<ParamEnum, String>()

    var closedPackages = listOf<CardPackage>()
    var closedPackagesQty = 0
    var handCards = listOf<Card>()
    var albumCards = mapOf<Family, Map<Int, Card>>()
    var playerCash = 0.0
    private var albumValue = 0.0
    var playerCashFormatted = ""
    var albumValueFormatted = ""
    var packsToAlert = 0

    fun startup() {
        ExternalUtil.getVersion(::versionCheck)

        refreshParams()

        if (!isNewUser()) {
            ExternalUtil.getPackageDispatch(params[ParamEnum.PLAYER_NAME]!!, ::packagesFound)
        } else {
            paramRepository.save(Param(ParamEnum.CASH, "0.00"))
        }

        refreshClosedPackages()
        refreshHandCards()
        refreshAlbum()
        refreshCash()
        refreshAlbumValue()
        TaskController.startup()
    }

    private fun versionCheck(version: String) {
        isVersionValid = version == VERSION
    }

    private fun packagesFound(packagesIdsFound: List<String>) {
        val existingPackages  = packageRepository.getPackagesById(packagesIdsFound)

        val existingIds = existingPackages.map { it.id }

        val toCreateIds = packagesIdsFound.filter { it !in existingIds }

        toCreateIds.forEach {
            createNewPackage(it, PackageOrigin.MATCH)
        }
        if (toCreateIds.size >= 0) {
            packsToAlert = toCreateIds.size
        }
    }

    private fun refreshAlbumValue() {
        albumValue = albumCards.values.flatMap { it.values }.sumOf { it.value }
        albumValueFormatted = UIUtil.decimalFormat.format(albumValue)
    }

    private fun refreshCash() {
        refreshParams()
        playerCash = params[ParamEnum.CASH]!!.toDouble()
        playerCashFormatted = UIUtil.decimalFormat.format(playerCash)
    }

    private fun refreshAlbum() {
        albumCards = cardRepository.getAlbumCards()
    }

    private fun refreshClosedPackages() {
        closedPackages = packageRepository.getClosedPackages()
        closedPackagesQty = closedPackages.size
    }

    private fun refreshHandCards() {
        handCards = cardRepository.getHandCards()
    }

    fun isNewUser(): Boolean = params[ParamEnum.PLAYER_NAME] == null

    fun newUserFlow(nickName: String) {
        paramRepository.save(Param(ParamEnum.PLAYER_NAME, nickName))
        refreshParams()
        ExternalUtil.getPackageDispatch(params[ParamEnum.PLAYER_NAME]!!, ::packagesFound)
    }

    private fun refreshParams() {
        params = paramRepository.getParams().associate { it.name to it.value }
    }

    private fun saveParam(param: Param) {
        paramRepository.save(param)
        refreshParams()
    }

    fun getNickName(): String {
        return params[ParamEnum.PLAYER_NAME]!!
    }

    fun openPackage(packageId: String): List<Card> {
        var ret = mutableListOf<Card>()
        for (i in 0..4) {
            ret.add(CardGenerator().generateRandom())
        }
        //Open Package in repo
        packageRepository.openPackage(packageId)
        //Save cards
        cardRepository.save(ret)
        refreshClosedPackages()
        refreshHandCards()
        saveParam(Param.increment(ParamEnum.PACKS_OPEN, params))
        TaskController.packOpen((params[ParamEnum.PACKS_OPEN]!!).toInt())

        return ret
    }

    fun openPackage(packageId: String, callBack: (List<Card>) -> Unit) {
        callBack(openPackage(packageId))
    }

    fun createNewPackage(orign: PackageOrigin = PackageOrigin.GIFT) {
        createNewPackage(UUID.randomUUID().toString(), orign)
        refreshClosedPackages()
    }

    fun createNewPackage(id: String, orign: PackageOrigin) {
        logger.info("Saving package id: $id (if not exists)")
        packageRepository.save(CardPackage(id, false, orign, Instant.now()))
        refreshClosedPackages()
    }

    fun glueCard(card: Card) {
        //Vende a anterior
        val cardInAlbum = albumCards[card.bluePrint.family]?.get(card.bluePrint.albumPosition)
        if (cardInAlbum != null) {
            sellCard(cardInAlbum)
        }
        //Save DB
        card.isGlued = true
        cardRepository.save(listOf(card))
        //Refresh hand
        refreshHandCards()
        //Refresh album
        refreshAlbum()
        refreshAlbumValue()
        if (cardInAlbum == null) {
            saveParam(Param.increment(ParamEnum.CARDS_PLACED, params))
        }
        TaskController.cardPlaced(totalCardsInAlbum(), albumValue)
    }

    private fun totalCardsInAlbum(): Int {
        var count = 0
        for (familyEntry in albumCards) {
            count += familyEntry.value.size
        }
        return count
    }

    fun canGlueCard(card: Card): Boolean {
        val cardInAlbum = albumCards[card.bluePrint.family]?.get(card.bluePrint.albumPosition)
        return if (cardInAlbum != null) {
            cardInAlbum.value < card.value
        } else {
            true
        }
    }

    fun isNewCard(card: Card): Boolean {
        return albumCards[card.bluePrint.family]?.get(card.bluePrint.albumPosition) == null
    }

    fun increaseMoney(value: Double) {
        paramRepository.save(Param(ParamEnum.CASH, (params[ParamEnum.CASH]!!.toDouble() + value).toString()))
        refreshCash()
    }

    fun sellCard(card: Card) {
        //Update cash
        increaseMoney(card.value)
        
        //Delete card
        cardRepository.delete(card)
        
        //refresh hand
        refreshHandCards()
        
        //Refresh cash
        refreshCash()

        saveParam(Param.increment(ParamEnum.CARDS_SOLD, params))
        TaskController.cardsSold((params[ParamEnum.CARDS_SOLD]!!).toInt())
    }

    fun purchase(quantity: Int, total: Double) {
        paramRepository.save(Param(ParamEnum.CASH, (params[ParamEnum.CASH]!!.toDouble() - total).toString()))
        for (i in 1..quantity) {
            createNewPackage(PackageOrigin.PURCHASE)
        }
        refreshCash()
        refreshClosedPackages()
    }

    fun getCardInfo(card: Card): String {
        if (isNewCard(card)) {
            return "NEW"
        }
        if (canGlueCard(card)) {
            return "BETTER"
        }
        return ""
    }

    fun getTotalOpenPacks(): Int {
        return params[ParamEnum.PACKS_OPEN]!!.toInt()
    }

    fun getTotalCardsSold(): Int {
        return params[ParamEnum.CARDS_SOLD]!!.toInt()
    }

    fun getTotalCardsPlaced(): Int {
        return params[ParamEnum.CARDS_PLACED]!!.toInt()
    }

}

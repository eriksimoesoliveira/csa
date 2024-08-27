package com.eriks.core

import com.eriks.core.be.BackendService
import com.eriks.core.be.dto.AlbumValueUpdate
import com.eriks.core.be.dto.LoginDtoOut
import com.eriks.core.be.dto.OPAPackageDto
import com.eriks.core.be.dto.PackOpenDto
import com.eriks.core.config.CardGenerator
import com.eriks.core.objects.*
import com.eriks.core.repository.CardRepository
import com.eriks.core.repository.PackageRepository
import com.eriks.core.repository.ParamRepository
import com.eriks.core.ui.util.UIUtil
import com.eriks.core.util.LoggerConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

object GameController {

    private val LOGGER: Logger = LoggerConfig.getLogger()

    const val VERSION = "1.7.2"
    var isVersionValid = false

    lateinit var packageRepository: PackageRepository
    lateinit var paramRepository: ParamRepository
    lateinit var cardRepository: CardRepository

    private var params = mapOf<ParamEnum, String>()

    var closedPackages = mapOf<CardPackage.Type, List<CardPackage>>()
    var closedPackagesQty = 0
    var handCards = listOf<Card>()
    var albumCards = mapOf<Family, Map<Int, Card>>()
    var playerCash = 0.0
    var albumValue = 0.0
    var playerCashFormatted = ""
    var albumValueFormatted = ""
    var packsToAlert = 0

    fun startup() {
        refreshParams()

        if (isNewUser()) {
            paramRepository.save(Param(ParamEnum.CASH, "0.00"))
        }

        refreshClosedPackages()
        refreshHandCards()
        refreshAlbum()
        refreshCash()
        refreshAlbumValue()
        TaskController.startup()
    }

    fun versionCheck() {
        runBlocking {
            val version = BackendService.getVersion()
            isVersionValid = version.contains(VERSION)
        }
    }

    suspend fun getRanking(): List<Ranking> {
        return BackendService.getRanking()
    }

    private fun packagesFound(packages: List<OPAPackageDto>) {
        packages.forEach {
            createNewPackage(it.packName, PackageOrigin.MATCH, CardPackage.Type.REGULAR, it.description)
        }
        if (packages.size >= 0) {
            packsToAlert = packages.size
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
        closedPackagesQty = closedPackages.values.sumOf { it.size }
    }

    private fun refreshHandCards() {
        handCards = cardRepository.getHandCards()
    }

    fun isNewUser(): Boolean = params[ParamEnum.PLAYER_NAME] == null

    fun newUserFlow(nickName: String) {
        val password = UUID.randomUUID().toString()
        val loginDtoOut = signUp(nickName, password)
        paramRepository.save(Param(ParamEnum.PLAYER_NAME, nickName))
        paramRepository.save(Param(ParamEnum.PLAYER_ID, loginDtoOut.userId))
        paramRepository.save(Param(ParamEnum.PLAYER_PASS, password))
        refreshParams()
    }

    fun checkForPackages() {
        runBlocking {
            val packages = BackendService.getPackages(params[ParamEnum.PLAYER_NAME]!!)
            packagesFound(packages)
        }
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

    suspend fun openPackage(cardPackage: CardPackage): List<Card> {
        runBlocking {
            try {
                withContext(Dispatchers.IO) {
                    BackendService.openPack(
                        PackOpenDto(
                            params[ParamEnum.PLAYER_ID]!!,
                            params[ParamEnum.PLAYER_NAME]!!,
                            cardPackage.id,
                            cardPackage.type.name,
                            cardPackage.origin.name
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LOGGER.log(Level.SEVERE,"Error when submitting openPack", e)
                exitProcess(0)
            }
        }

        var ret = mutableListOf<Card>()
        for (i in 0..4) {
            when (cardPackage.type) {
                CardPackage.Type.REGULAR -> ret.add(CardGenerator.generateRegularPackageCard())
                CardPackage.Type.RED -> ret.add(CardGenerator.generateRedPackageCard())
                CardPackage.Type.WHITE -> ret.add(CardGenerator.generateWhitePackageCard())
            }
        }
        //Open Package in repo
        packageRepository.openPackage(cardPackage.id)
        //Save cards
        cardRepository.save(ret)
        refreshClosedPackages()
        refreshHandCards()
        saveParam(Param.increment(ParamEnum.PACKS_OPEN, params))
        TaskController.packOpen((params[ParamEnum.PACKS_OPEN]!!).toInt())

        return ret
    }

    fun createNewPackage(orign: PackageOrigin, type: CardPackage.Type) {
        createNewPackage(UUID.randomUUID().toString(), orign, type, "")
    }

    private fun createNewPackage(id: String, orign: PackageOrigin, type: CardPackage.Type, description: String) {
        packageRepository.save(CardPackage(id, false, orign, Instant.now(), type, description))
        refreshClosedPackages()
    }

    suspend fun glueCard(card: Card) {
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
        runBlocking {
            try {
                withContext(Dispatchers.IO) {
                    BackendService.albumValueUpdate(
                        AlbumValueUpdate(
                            params[ParamEnum.PLAYER_ID]!!,
                            params[ParamEnum.PLAYER_NAME]!!,
                            albumValue
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LOGGER.log(Level.SEVERE,"Error when submitting albumValue", e)
                exitProcess(0)
            }
        }
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

    fun purchase(regularQuantity: Int, wwQuantity: Int, pinkPackage: Int, total: Double) {
        paramRepository.save(Param(ParamEnum.CASH, (params[ParamEnum.CASH]!!.toDouble() - total).toString()))
        for (i in 1..regularQuantity) {
            createNewPackage(PackageOrigin.PURCHASE, CardPackage.Type.REGULAR)
        }
        for (i in 1..wwQuantity) {
            createNewPackage(PackageOrigin.PURCHASE, CardPackage.Type.RED)
        }
        for (i in 1..pinkPackage) {
            createNewPackage(PackageOrigin.PURCHASE, CardPackage.Type.WHITE)
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

    fun login() {
        runBlocking {
            withContext(Dispatchers.Default) {
                BackendService.login(params[ParamEnum.PLAYER_ID]!!, params[ParamEnum.PLAYER_PASS]!!)
            }
        }
    }

    private fun signUp(userName: String, password: String): LoginDtoOut {
        return runBlocking {
            withContext(Dispatchers.Default) {
                BackendService.signUp(userName, password)
            }
        }
    }
}

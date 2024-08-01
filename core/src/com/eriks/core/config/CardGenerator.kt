package com.eriks.core.config

import com.eriks.core.objects.Card
import util.RandomUtil
import java.util.*

object CardGenerator {

    fun generateRegularPackageCard(): Card {
        //Random rarity
        val rarity = DropConfig.generateRarity(RandomUtil.randomInt(100))
        //All items in the collection with that rarity
        val rarityItemsInTheCollection = DropConfig.getAllBluePrints().filter { it.rarity == rarity }
        //Randomly choose item with rarity in that collection
        val generatedModel = RandomUtil.randomElementFromList(rarityItemsInTheCollection)
        //Randomly generate float
        val float = DropConfig.generateFloat(RandomUtil.randomInt(100))

        return Card(UUID.randomUUID().toString(), generatedModel, float, WeaponValue.generateCardValue(generatedModel, float), false)
    }

    fun generateRedPackageCard(): Card {
        //Random rarity
        val rarity = DropConfig.generateRarity(RandomUtil.randomInt(100))
        //All items in the collection with that rarity
        val rarityItemsInTheCollection = DropConfig.getAllBluePrints().filter { it.rarity == rarity }
        //Randomly choose item with rarity in that collection
        val generatedModel = RandomUtil.randomElementFromList(rarityItemsInTheCollection)
        //Randomly generate float
        val float = DropConfig.generateRedFloat(RandomUtil.randomInt(100))

        return Card(UUID.randomUUID().toString(), generatedModel, float, WeaponValue.generateCardValue(generatedModel, float), false)
    }

    fun generateWhitePackageCard(): Card {
        //Random rarity
        val rarity = DropConfig.generateRarityWhite(RandomUtil.randomInt(100))
        //All items in the collection with that rarity
        val rarityItemsInTheCollection = DropConfig.getAllBluePrints().filter { it.rarity == rarity }
        //Randomly choose item with rarity in that collection
        val generatedModel = RandomUtil.randomElementFromList(rarityItemsInTheCollection)
        //Randomly generate float
        val float = DropConfig.generateRedFloat(RandomUtil.randomInt(100))

        return Card(UUID.randomUUID().toString(), generatedModel, float, WeaponValue.generateCardValue(generatedModel, float), false)
    }
}
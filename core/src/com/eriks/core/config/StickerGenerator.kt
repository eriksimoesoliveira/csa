package com.eriks.core.config

import com.eriks.core.objects.Family
import com.eriks.core.objects.Card
import util.RandomUtil
import java.util.*

class CardGenerator {

    fun generateRandom(collection: Family): Card {
        //Random rarity
        val rarity = DropConfig.generateRarity(RandomUtil.randomInt(100))
        //All items in the collection with that rarity !!! BROKEN -> existem colecoes que nao tem todas as raridades
        val rarityItemsInTheCollection = DropConfig.getAllBluePrintsOfCollection(collection).filter { it.rarity == rarity }
        //Randomly choose item with rarity in that collection
        val generatedModel = RandomUtil.randomElementFromList(rarityItemsInTheCollection)
        //Randomly generate float
        val float = DropConfig.generateFloat(RandomUtil.randomInt(100))

        return Card(UUID.randomUUID().toString(), generatedModel, float, WeaponValue.generateCardValue(generatedModel, float), false)
    }

    fun generateRandom(): Card {
        //Random rarity
        val rarity = DropConfig.generateRarity(RandomUtil.randomInt(100))
        //All items in the collection with that rarity
        val rarityItemsInTheCollection = DropConfig.getAllBluePrints().filter { it.rarity == rarity }
        //Randomly choose item with rarity in that collection
        val generatedModel = RandomUtil.randomElementFromList(rarityItemsInTheCollection)// rarityItemsInTheCollection[RandomUtil.randomIntBetween(0, rarityItemsInTheCollection.size - 1)]
        //Randomly generate float
        val float = DropConfig.generateFloat(RandomUtil.randomInt(100))

        return Card(UUID.randomUUID().toString(), generatedModel, float, WeaponValue.generateCardValue(generatedModel, float), false)
    }
}
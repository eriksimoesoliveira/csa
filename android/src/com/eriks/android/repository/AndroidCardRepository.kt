package com.eriks.android.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import com.eriks.core.objects.Card
import com.eriks.core.objects.CardBluePrint
import com.eriks.core.objects.Condition
import com.eriks.core.objects.Family
import com.eriks.core.repository.CardRepository

class AndroidCardRepository(private val database: SQLiteDatabase): CardRepository {

    override fun save(cards: List<Card>) {
        for (card in cards) {
            database.insertWithOnConflict("STICKER", null, serialize(card), CONFLICT_REPLACE)
        }
    }

    override fun getHandCards(): List<Card> {
        val ret = mutableListOf<Card>()
        val cursor = database.query("STICKER", arrayOf("id", "blueprint", "float", "value", "is_glued"), "is_glued is ?", arrayOf("0"), null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            ret.add(
                Card(
                    cursor.getString(0),
                    CardBluePrint.valueOf(cursor.getString(1)),
                    Condition.valueOf(cursor.getString(2)),
                    cursor.getDouble(3),
                    cursor.getString(4).toBoolean()
                )
            )
        }
        cursor?.close()
        return ret
    }

    override fun delete(card: Card) {
        database.delete("STICKER", "id = ?", arrayOf(card.id))
    }

    override fun getAlbumCards(): Map<Family, Map<Int, Card>> {
        val list = mutableListOf<Card>()
        val cursor = database.query("STICKER", arrayOf("id", "blueprint", "float", "value", "is_glued"), "is_glued is ?", arrayOf("1"), null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            list.add(
                Card(
                    cursor.getString(0),
                    CardBluePrint.valueOf(cursor.getString(1)),
                    Condition.valueOf(cursor.getString(2)),
                    cursor.getDouble(3),
                    cursor.getString(4).toBoolean()
                )
            )
        }
        cursor?.close()
        return list.groupBy { it.bluePrint.family }.mapValues { entry -> entry.value.associateBy { it.bluePrint.albumPosition } }
    }

    private fun serialize(card: Card): ContentValues {
        val contentValue = ContentValues()
        contentValue.put("id", card.id)
        contentValue.put("blueprint", card.bluePrint.name)
        contentValue.put("float", card.weaponFloat.name)
        contentValue.put("value", card.value)
        contentValue.put("is_glued", card.isGlued)
        return contentValue
    }
}
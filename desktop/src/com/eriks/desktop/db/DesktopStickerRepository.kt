package com.eriks.desktop.db

import com.eriks.core.objects.Family
import com.eriks.core.objects.Card
import com.eriks.core.objects.CardBluePrint
import com.eriks.core.objects.Condition
import com.eriks.core.repository.CardRepository

class DesktopCardRepository: CardRepository {

    override fun save(cards: List<Card>) {
        val prepareStatement = Database.conn.prepareStatement("INSERT OR REPLACE INTO STICKER (id, blueprint, float, value, is_glued) VALUES (?, ?, ?, ?, ?)")
        cards.forEach { card ->
            prepareStatement.setString(1, card.id)
            prepareStatement.setString(2, card.bluePrint.name)
            prepareStatement.setString(3, card.weaponFloat.name)
            prepareStatement.setDouble(4, card.value)
            prepareStatement.setBoolean(5, card.isGlued)
            prepareStatement.execute()
        }
        prepareStatement.close()
    }

    override fun getHandCards(): List<Card> {
        val ret = mutableListOf<Card>()
        val preparedStatement = Database.conn.prepareStatement("SELECT id, blueprint, float, value, is_glued FROM STICKER WHERE is_glued is false")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(
                Card(
                    resultSet.getString(1),
                    CardBluePrint.valueOf(resultSet.getString(2)),
                    Condition.valueOf(resultSet.getString(3)),
                    resultSet.getDouble(4),
                    resultSet.getString(5).toBoolean()
                )
            )
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }

    override fun getAlbumCards(): Map<Family, Map<Int, Card>> {
        val list = mutableListOf<Card>()
        val preparedStatement = Database.conn.prepareStatement("SELECT id, blueprint, float, value, is_glued FROM STICKER WHERE is_glued is true")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            list.add(
                Card(
                    resultSet.getString(1),
                    CardBluePrint.valueOf(resultSet.getString(2)),
                    Condition.valueOf(resultSet.getString(3)),
                    resultSet.getDouble(4),
                    resultSet.getString(5).toBoolean()
                )
            )
        }
        resultSet.close()
        preparedStatement.close()
        return list.groupBy { it.bluePrint.family }.mapValues { entry -> entry.value.associateBy { it.bluePrint.albumPosition } }
    }

    override fun delete(card: Card) {
        val prepareStatement = Database.conn.prepareStatement("DELETE FROM STICKER WHERE id = ?")
        prepareStatement.setString(1, card.id)
        prepareStatement.execute()
        prepareStatement.close()
    }
}
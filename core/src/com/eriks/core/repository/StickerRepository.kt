package com.eriks.core.repository

import com.eriks.core.objects.Family
import com.eriks.core.objects.Card

interface CardRepository {
    fun save(cards: List<Card>)
    fun getHandCards(): List<Card>
    fun getAlbumCards(): Map<Family, Map<Int, Card>>
    fun delete(card: Card)
}
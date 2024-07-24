package util

import kotlin.random.Random

object RandomUtil {
    fun randomIntBetween(lower: Int, higher: Int) = if (lower == higher) lower else Random.Default.nextInt(lower, higher)

    fun <T> randomElementFromList(list: List<T>): T = list[randomIntBetween(0, list.size)]

    fun randomInt(max: Int): Int = Random.Default.nextInt(max)
}
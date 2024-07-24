package com.eriks.core.objects.tasks

object ProgressionTaskLogics {

    private var lastRewardedAlbumValue: Double = 0.0

    fun evaluatePacksOpen(totalPacksOpen: Int): Int? {
        return if (totalPacksOpen % 10 == 0) {
            100
        } else {
            null
        }
    }

    fun evaluateCardsPlaced(totalCardsPlaced: Int): Int? {
        return if (totalCardsPlaced % 20 == 0) {
            // Calculate the reward based on the number of milestones reached
            val milestonesReached = totalCardsPlaced / 20
            100 * (1 shl (milestonesReached - 1))  // Exponential reward progression
        } else {
            null
        }
    }

    fun evaluateCardsSold(totalCardsSold: Int): Int? {
        return if (totalCardsSold % 30 == 0) {
            15
        } else {
            null
        }
    }

    fun evaluateAlbumValue(totalAlbumValue: Double): Int? {
        val closestMultipleOf500 = (totalAlbumValue / 500).toInt() * 500.0

        return if (closestMultipleOf500 > lastRewardedAlbumValue) {
            lastRewardedAlbumValue = closestMultipleOf500
            (closestMultipleOf500 / 5).toInt()
        } else {
            null
        }
    }

    fun getNextPacksOpenMilestone(currentPacksOpen: Int): Pair<Int, Int> {
        val nextMilestone = ((currentPacksOpen / 10) + 1) * 10
        val reward = 100
        return Pair(nextMilestone, reward)
    }

    fun getNextCardsPlacedMilestone(currentCardsPlaced: Int): Pair<Int, Int> {
        val nextMilestone = ((currentCardsPlaced / 20) + 1) * 20
        // Calculate the reward based on the number of milestones reached
        val milestonesReached = nextMilestone / 20
        val reward = 100 * (1 shl (milestonesReached - 1))  // Exponential reward progression
        return Pair(nextMilestone, reward)
    }

    fun getNextCardsSoldMilestone(currentCardsSold: Int): Pair<Int, Int> {
        val nextMilestone = ((currentCardsSold / 30) + 1) * 30
        val reward = 15
        return Pair(nextMilestone, reward)
    }

    fun getNextAlbumValueMilestone(currentAlbumValue: Double): Pair<Double, Double> {
        val nextMilestone = ((currentAlbumValue / 500).toInt() + 1) * 500.0
        val reward = nextMilestone / 5
        return Pair(nextMilestone, reward)
    }
}
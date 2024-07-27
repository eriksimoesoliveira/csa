package com.eriks.core.objects.tasks

object ProgressionTaskLogics {

    fun getNextPacksOpenMilestone(currentPacksOpen: Int): Pair<Int, Int> {
        val nextMilestone = ((currentPacksOpen / 10) + 1) * 10
        val reward = 100
        return Pair(nextMilestone, reward)
    }

    fun getNextCardsPlacedMilestone(currentCardsPlaced: Int): Pair<Int, Int> {
        val nextMilestone = ((currentCardsPlaced / 20) + 1) * 20
        // Calculate the reward based on the number of milestones reached
        val milestonesReached = nextMilestone / 20
        val reward = 50 * (1 shl (milestonesReached - 1))  // Exponential reward progression
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
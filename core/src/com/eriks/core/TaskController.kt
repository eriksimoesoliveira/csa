package com.eriks.core

import com.eriks.core.objects.Family
import com.eriks.core.objects.Condition
import com.eriks.core.objects.tasks.*
import com.eriks.core.repository.TaskRepository
import java.util.*

object TaskController {

    lateinit var taskRepository: TaskRepository
    val collectionTasks: MutableList<CollectionTask> = mutableListOf()
    var progressionTasks: MutableList<ProgressionTask> = mutableListOf()
    var boardLevelMap = mutableMapOf<Family, Map<Condition, Int>>()
    var unclaimedQtyFormatted = "0"

    fun startup() {
        refreshCollectionTasks()
        refreshProgressionTasks()
    }

    private fun refreshCollectionTasks() {
        collectionTasks.clear()

        val completedTasks: List<CollectionTask> = taskRepository.getCollectionTasks()

        CollectionTaskEnum.values().forEach { taskEnum ->
            val found = completedTasks.firstOrNull {
                it.collectionTaskEnum.weaponFloat == taskEnum.weaponFloat &&
                        it.collectionTaskEnum.family == taskEnum.family
            } ?: CollectionTask(false, false, taskEnum)

            collectionTasks.add(found)
        }

        Family.values().forEach {
            calculateBoardCompletionTasks(it, false)
        }
        updateUnclaimedQtyFormatted()
    }

    private fun updateUnclaimedQtyFormatted() {
        unclaimedQtyFormatted = (collectionTasks.filter { it.isCompleted && !it.isClaimed }.size + progressionTasks.filter { it.isCompleted && !it.isClaimed  }.size).toString()
    }

    private fun refreshProgressionTasks() {
        progressionTasks = taskRepository.getProgressionTasks()
        updateUnclaimedQtyFormatted()
    }

    fun packOpen(totalPackOpen: Int) {
        //Get current milestone
        var currentMilestone =
            progressionTasks.filter { !it.isCompleted && it.taskEnum == ProgressionTaskEnum.PACKS_OPEN_TASK }.firstOrNull()

        //If not found create it
        if (currentMilestone == null) {
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextPacksOpenMilestone(totalPackOpen)
            currentMilestone = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.PACKS_OPEN_TASK,
                "Have opened a total of ${nextPacksOpenMilestone.first} packs",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first)
            taskRepository.saveProgressionTask(currentMilestone)
            refreshProgressionTasks()
        }

        //Checks completion
        if (totalPackOpen >= currentMilestone.milestone) {
            //Set it to claimable
            currentMilestone.isClaimed = false
            currentMilestone.isCompleted = true
            taskRepository.saveProgressionTask(currentMilestone)
            //Create a new one
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextPacksOpenMilestone(totalPackOpen)
            val nextProgressionTask = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.PACKS_OPEN_TASK,
                "Have opened a total of ${nextPacksOpenMilestone.first} packs",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first
            )
            taskRepository.saveProgressionTask(nextProgressionTask)
            refreshProgressionTasks()
        }
    }

    fun cardsSold(totalCardsSold: Int) {
        //Get current milestone
        var currentMilestone =
            progressionTasks.filter { !it.isCompleted && it.taskEnum == ProgressionTaskEnum.CARDS_SOLD_TASK }.firstOrNull()

        //If not found create it
        if (currentMilestone == null) {
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextCardsSoldMilestone(totalCardsSold)
            currentMilestone = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.CARDS_SOLD_TASK,
                "Have sold a total of ${nextPacksOpenMilestone.first} cards",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first)
            taskRepository.saveProgressionTask(currentMilestone)
            refreshProgressionTasks()
        }

        //Checks completion
        if (totalCardsSold >= currentMilestone.milestone) {
            //Set it to claimable
            currentMilestone.isClaimed = false
            currentMilestone.isCompleted = true
            taskRepository.saveProgressionTask(currentMilestone)
            //Create a new one
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextCardsSoldMilestone(totalCardsSold)
            val nextProgressionTask = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.CARDS_SOLD_TASK,
                "Have sold a total of ${nextPacksOpenMilestone.first} cards",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first
            )
            taskRepository.saveProgressionTask(nextProgressionTask)
            refreshProgressionTasks()
        }
    }

    fun cardPlaced(totalCardPlaced: Int, albumValue: Double) {
        //Get current milestone
        var currentMilestone =
            progressionTasks.filter { !it.isCompleted && it.taskEnum == ProgressionTaskEnum.CARDS_PLACED_TASK }.firstOrNull()

        //If not found create it
        if (currentMilestone == null) {
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextCardsPlacedMilestone(totalCardPlaced)
            currentMilestone = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.CARDS_PLACED_TASK,
                "Have placed a total of ${nextPacksOpenMilestone.first} cards",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first)
            taskRepository.saveProgressionTask(currentMilestone)
            refreshProgressionTasks()
        }

        //Checks completion
        if (totalCardPlaced >= currentMilestone.milestone) {
            //Set it to claimable
            currentMilestone.isClaimed = false
            currentMilestone.isCompleted = true
            taskRepository.saveProgressionTask(currentMilestone)
            //Create a new one
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextCardsPlacedMilestone(totalCardPlaced)
            val nextProgressionTask = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.CARDS_PLACED_TASK,
                "Have placed a total of ${nextPacksOpenMilestone.first} cards",
                nextPacksOpenMilestone.second,
                false,
                false,
                nextPacksOpenMilestone.first
            )
            taskRepository.saveProgressionTask(nextProgressionTask)
            refreshProgressionTasks()
        }

        refreshCollectionTasks()
        albumValueIncreased(albumValue)
    }

    private fun albumValueIncreased(totalAlbumValue: Double) {
        //Get current milestone
        var currentMilestone =
            progressionTasks.filter { !it.isCompleted && it.taskEnum == ProgressionTaskEnum.ALBUM_VALUE_TASK }.firstOrNull()

        //If not found create it
        if (currentMilestone == null) {
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextAlbumValueMilestone(totalAlbumValue)
            currentMilestone = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.ALBUM_VALUE_TASK,
                "Album worth more than $${nextPacksOpenMilestone.first}",
                nextPacksOpenMilestone.second.toInt(),
                false,
                false,
                nextPacksOpenMilestone.first.toInt())
            taskRepository.saveProgressionTask(currentMilestone)
            refreshProgressionTasks()
        }

        //Checks completion
        if (totalAlbumValue >= currentMilestone.milestone) {
            //Set it to claimable
            currentMilestone.isClaimed = false
            currentMilestone.isCompleted = true
            taskRepository.saveProgressionTask(currentMilestone)
            //Create a new one
            val nextPacksOpenMilestone = ProgressionTaskLogics.getNextAlbumValueMilestone(totalAlbumValue)
            val nextProgressionTask = ProgressionTask(
                UUID.randomUUID().toString(),
                ProgressionTaskEnum.ALBUM_VALUE_TASK,
                "Album worth more than $${nextPacksOpenMilestone.first}",
                nextPacksOpenMilestone.second.toInt(),
                false,
                false,
                nextPacksOpenMilestone.first.toInt()
            )
            taskRepository.saveProgressionTask(nextProgressionTask)
            refreshProgressionTasks()
        }
    }

    private fun calculateBoardCompletionTasks(family: Family, justPopulate: Boolean) {
        val collectionCards = GameController.albumCards[family]?.values ?: listOf()

        // Initialize the map with zero values for each condition
        val collectionLevelMap = mutableMapOf<Condition, Int>().apply {
            Condition.values().forEach { this[it] = 0 }
        }

        // Update the count for each condition
        collectionCards.forEach { card ->
            Condition.values().forEach { weaponFloat ->
                if (card.weaponFloat.level >= weaponFloat.level) {
                    collectionLevelMap[weaponFloat] = collectionLevelMap[weaponFloat]!! + 1
                }
            }
        }

        // Save completed tasks if not just populating
        if (!justPopulate) {
            var tasksCompleted = false
            Condition.values().forEach { weaponFloat ->
                if (collectionLevelMap[weaponFloat] == 20) {
                    val taskEnum = CollectionTaskEnum.getTaskByCollectionAndFloat(family, weaponFloat)
                    val existingTask = collectionTasks.find { it.collectionTaskEnum == taskEnum }

                    if (existingTask == null || !existingTask.isCompleted) {
                        taskRepository.saveCollectionTask(CollectionTask(false, true, taskEnum))
                        tasksCompleted = true
                    }
                }
            }
            if (tasksCompleted) {
                refreshCollectionTasks()
            }
        }

        // Update the board level map
        boardLevelMap[family] = collectionLevelMap
    }


    fun printTasks() {
        collectionTasks.forEach {
            println("${it.collectionTaskEnum.family}|${it.collectionTaskEnum.weaponFloat}|${it.isClaimed}|${boardLevelMap[it.collectionTaskEnum.family]!![it.collectionTaskEnum.weaponFloat]}")

        }
    }

    fun getCollectionTaskProgressionFormatted(family: Family, weaponFloat: Condition): String {
        return "${boardLevelMap[family]!![weaponFloat]!!}/20"
    }

    fun claimTaskProgression(task: ProgressionTask) {
        //Insert money
        GameController.increaseMoney(task.rewardValue.toDouble())

        //Update task
//        taskRepository.claimProgressionTask(task.id)
        taskRepository.deleteProgressionTask(task)

        //Refresh
        refreshProgressionTasks()
    }

    fun claimTaskCollection(task: CollectionTask) {
        //Insert money
        GameController.increaseMoney(task.collectionTaskEnum.reward.toDouble())

        //Update task
        taskRepository.claimCollectionTask(task)

        //Refresh
        refreshCollectionTasks()
    }
}
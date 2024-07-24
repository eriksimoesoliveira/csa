package com.eriks.core.repository

import com.eriks.core.objects.tasks.CollectionTask
import com.eriks.core.objects.tasks.ProgressionTask

interface TaskRepository {
    fun getCollectionTasks(): List<CollectionTask>
    fun saveCollectionTask(collectionTask: CollectionTask)
    fun claimCollectionTask(collectionTask: CollectionTask)

//    fun getUnclaimedProgressionTasks(): MutableList<ProgressionTask>
    fun getProgressionTasks(): MutableList<ProgressionTask>
    fun saveProgressionTask(progressionTask: ProgressionTask)
//    fun claimProgressionTask(taskId: String)
    fun deleteProgressionTask(progressionTask: ProgressionTask)

}
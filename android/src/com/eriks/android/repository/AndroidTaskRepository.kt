package com.eriks.android.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.eriks.core.objects.tasks.CollectionTask
import com.eriks.core.objects.tasks.CollectionTaskEnum
import com.eriks.core.objects.tasks.ProgressionTask
import com.eriks.core.objects.tasks.ProgressionTaskEnum
import com.eriks.core.repository.TaskRepository

class AndroidTaskRepository(private val db: SQLiteDatabase): TaskRepository {

    override fun getCollectionTasks(): List<CollectionTask> {
        val ret = mutableListOf<CollectionTask>()
        val cursor: Cursor = db.query(
            "COLLECTION_TASK",
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            ret.add(
                CollectionTask(
                    cursor.getInt(cursor.getColumnIndexOrThrow("is_claimed")) > 0,
                    true,
                    CollectionTaskEnum.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("collection_task")))
                )
            )
        }
        cursor.close()
        return ret
    }

    override fun saveCollectionTask(collectionTask: CollectionTask) {
        val contentValues = ContentValues().apply {
            put("collection_task", collectionTask.collectionTaskEnum.name)
            put("is_claimed", collectionTask.isClaimed)
        }
        db.insert("COLLECTION_TASK", null, contentValues)
    }

    override fun claimCollectionTask(collectionTask: CollectionTask) {
        val contentValues = ContentValues().apply {
            put("is_claimed", true)
        }
        db.update(
            "COLLECTION_TASK",
            contentValues,
            "collection_task = ?",
            arrayOf(collectionTask.collectionTaskEnum.name)
        )
    }

    override fun saveProgressionTask(progressionTask: ProgressionTask) {
        val contentValues = ContentValues().apply {
            put("id", progressionTask.id)
            put("param_name", progressionTask.taskEnum.name)
            put("task_description", progressionTask.description)
            put("milestone", progressionTask.milestone)
            put("is_completed", progressionTask.isCompleted)
            put("is_claimed", progressionTask.isClaimed)
            put("reward", progressionTask.rewardValue)
        }
        db.insertWithOnConflict("PROGRESSION_TASK", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }

    override fun getProgressionTasks(): MutableList<ProgressionTask> {
        val ret = mutableListOf<ProgressionTask>()
        val cursor: Cursor = db.query(
            "PROGRESSION_TASK",
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            ret.add(
                ProgressionTask(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    ProgressionTaskEnum.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("param_name"))),
                    cursor.getString(cursor.getColumnIndexOrThrow("task_description")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("reward")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("is_claimed")) > 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow("is_completed")) > 0,
                    cursor.getInt(cursor.getColumnIndexOrThrow("milestone"))
                )
            )
        }
        cursor.close()
        return ret
    }

    override fun deleteProgressionTask(progressionTask: ProgressionTask) {
        db.delete(
            "PROGRESSION_TASK",
            "id = ?",
            arrayOf(progressionTask.id)
        )
    }

//    override fun getCollectionTasks(): List<CollectionTask> {
//        val ret = mutableListOf<CollectionTask>()
//        val cursor = database.query("COLLECTION_TASK", arrayOf("collection_task", "is_claimed"), null, null, null, null, null)
//        while (cursor != null && cursor.moveToNext()) {
//            ret.add(
//                CollectionTask(
//                    cursor.getString(0).toBoolean(),
//                    true,
//                    CollectionTaskEnum.valueOf(cursor.getString(1))
//                )
//            )
//        }
//        cursor?.close()
//        return ret
//    }
//
//    override fun saveCollectionTask(collectionTask: CollectionTask) {
//        database.insert("COLLECTION_TASK", null, serialize(collectionTask))
//    }
//
//    private fun serialize(collectionTask: CollectionTask): ContentValues {
//        val contentValue = ContentValues()
//        contentValue.put("collection_task", collectionTask.collectionTaskEnum.name)
//        contentValue.put("is_claimed", collectionTask.isClaimed)
//        return contentValue
//    }
//
//    override fun claimCollectionTask(collectionTask: CollectionTask) {
//        database.rawQuery("UPDATE COLLECTION_TASK SET is_claimed = true where collection_task = ?", )
//    }
//
//    override fun getProgressionTasks(): MutableList<ProgressionTask> {
//        TODO("Not yet implemented")
//    }
//
//    override fun saveProgressionTask(progressionTask: ProgressionTask) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteProgressionTask(progressionTask: ProgressionTask) {
//        TODO("Not yet implemented")
//    }
}
package com.eriks.desktop.db

import com.eriks.core.objects.tasks.CollectionTask
import com.eriks.core.objects.tasks.CollectionTaskEnum
import com.eriks.core.objects.tasks.ProgressionTask
import com.eriks.core.objects.tasks.ProgressionTaskEnum
import com.eriks.core.repository.TaskRepository

class DesktopTaskRepository: TaskRepository {

    override fun getCollectionTasks(): List<CollectionTask> {
        val ret = mutableListOf<CollectionTask>()
        val preparedStatement = Database.conn.prepareStatement("SELECT * FROM COLLECTION_TASK")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(
                CollectionTask(
                    resultSet.getBoolean("is_claimed"),
                    true,
                    CollectionTaskEnum.valueOf(resultSet.getString("collection_task"))
                )
            )
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }

    override fun saveCollectionTask(collectionTask: CollectionTask) {
        val prepareStatement = Database.conn.prepareStatement("INSERT INTO COLLECTION_TASK (collection_task, is_claimed) VALUES (?, ?)")
        prepareStatement.setString(1, collectionTask.collectionTaskEnum.name)
        prepareStatement.setBoolean(2, collectionTask.isClaimed)
        prepareStatement.execute()
        prepareStatement.close()
    }

    override fun claimCollectionTask(collectionTask: CollectionTask) {
        val preparedStatement = Database.conn.prepareStatement("UPDATE COLLECTION_TASK SET is_claimed = true where collection_task = ?")
        preparedStatement.setString(1, collectionTask.collectionTaskEnum.name)
        preparedStatement.execute()
        preparedStatement.close()
    }

    override fun saveProgressionTask(progressionTask: ProgressionTask) {
        val prepareStatement = Database.conn.prepareStatement("INSERT OR REPLACE INTO PROGRESSION_TASK (" +
                "id, " +
                "param_name, " +
                "task_description, " +
                "milestone," +
                "is_completed," +
                "is_claimed," +
                "reward) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)")
        prepareStatement.setString(1, progressionTask.id)
        prepareStatement.setString(2, progressionTask.taskEnum.name)
        prepareStatement.setString(3, progressionTask.description)
        prepareStatement.setInt(4, progressionTask.milestone)
        prepareStatement.setBoolean(5, progressionTask.isCompleted)
        prepareStatement.setBoolean(6, progressionTask.isClaimed)
        prepareStatement.setInt(7, progressionTask.rewardValue)
        prepareStatement.execute()
        prepareStatement.close()
    }

    override fun getProgressionTasks(): MutableList<ProgressionTask> {
        val ret = mutableListOf<ProgressionTask>()
        val preparedStatement = Database.conn.prepareStatement("SELECT * FROM PROGRESSION_TASK")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(
                ProgressionTask(
                    resultSet.getString("id"),
                    ProgressionTaskEnum.valueOf(resultSet.getString("param_name")),
                    resultSet.getString("task_description"),
                    resultSet.getInt("reward"),
                    resultSet.getBoolean("is_claimed"),
                    resultSet.getBoolean("is_completed"),
                    resultSet.getInt("milestone"))
            )
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }

    override fun deleteProgressionTask(progressionTask: ProgressionTask) {
        val preparedStatement = Database.conn.prepareStatement("DELETE FROM PROGRESSION_TASK where id = ?")
        preparedStatement.setString(1, progressionTask.id)
        preparedStatement.execute()
        preparedStatement.close()
    }
}
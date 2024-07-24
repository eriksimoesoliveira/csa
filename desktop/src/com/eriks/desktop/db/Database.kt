package com.eriks.desktop.db

import com.eriks.core.repository.Tables
import java.io.File
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Database {

    lateinit var conn: Connection

    init {
        try {
            // Construct the full path to the database file
            val localAppData = System.getenv("LOCALAPPDATA")
            val dbPath = Paths.get(localAppData, "csa", "csa.db").toString()

            // Ensure the directory exists
            val dbDir = File(dbPath).parentFile
            if (!dbDir.exists()) {
                dbDir.mkdirs()
            }

            conn = DriverManager.getConnection("jdbc:sqlite:$dbPath")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        setupDatabase()
    }


    private fun setupDatabase() {
        try {
            val createStatement = conn.createStatement()
            createStatement.executeUpdate(Tables.CREATE_TABLE_PACKAGE)
            createStatement.executeUpdate(Tables.CREATE_TABLE_PARAM)
            createStatement.executeUpdate(Tables.CREATE_TABLE_STICKER)
            createStatement.executeUpdate(Tables.CREATE_TABLE_COLLECTION_TASK)
            createStatement.executeUpdate(Tables.CREATE_TABLE_PROGRESSION_TASK)
            createStatement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}
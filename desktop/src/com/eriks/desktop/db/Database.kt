package com.eriks.desktop.db

import com.eriks.core.repository.Tables
import com.eriks.core.repository.Tables.DATABASE_NAME
import com.eriks.core.util.LoggerConfig
import java.io.File
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level

object Database {

    lateinit var conn: Connection

    init {
        try {
            // Construct the full path to the database file
            val localAppData = System.getenv("LOCALAPPDATA")
            val dbPath = Paths.get(localAppData, "csa", DATABASE_NAME).toString()

            // Ensure the directory exists
            val dbDir = File(dbPath).parentFile
            if (!dbDir.exists()) {
                dbDir.mkdirs()
            }

            conn = DriverManager.getConnection("jdbc:sqlite:$dbPath")
            setupDatabase()

        } catch (e: SQLException) {
            e.printStackTrace()
            LoggerConfig.getLogger().log(Level.SEVERE, "SQL Error", e)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to initialize database", e)
        }
    }

    private fun addNewColumnIfNotExists() {
        if (!doesColumnExist("type")) {
            try {
                val stmt = conn.createStatement()
                stmt.executeUpdate("ALTER TABLE PACKAGE ADD COLUMN type TEXT default 'REGULAR'")
                stmt.close()
            } catch (e: SQLException) {
                e.printStackTrace()
                LoggerConfig.getLogger().log(Level.SEVERE, "SQL Error", e)
            }
        }
    }

    private fun doesColumnExist(columnName: String): Boolean {
        return try {
            val metaData: DatabaseMetaData = conn.metaData
            val rs = metaData.getColumns(null, null, "PACKAGE", columnName)
            rs.next()
        } catch (e: SQLException) {
            e.printStackTrace()
            LoggerConfig.getLogger().log(Level.SEVERE, "SQL Error", e)
            false
        }
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
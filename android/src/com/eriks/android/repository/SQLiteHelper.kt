package com.eriks.android.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eriks.core.repository.Tables
import com.eriks.core.repository.Tables.DATABASE_NAME

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {

    companion object {
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tables.CREATE_TABLE_PACKAGE)
        db.execSQL(Tables.CREATE_TABLE_PARAM)
        db.execSQL(Tables.CREATE_TABLE_STICKER)
        db.execSQL(Tables.CREATE_TABLE_COLLECTION_TASK)
        db.execSQL(Tables.CREATE_TABLE_PROGRESSION_TASK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }


}

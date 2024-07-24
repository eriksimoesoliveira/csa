package com.eriks.android.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.eriks.core.repository.Tables

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "csa.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tables.CREATE_TABLE_PACKAGE)
        db.execSQL(Tables.CREATE_TABLE_PARAM)
        db.execSQL(Tables.CREATE_TABLE_STICKER)
        db.execSQL(Tables.CREATE_TABLE_COLLECTION_TASK)
        db.execSQL(Tables.CREATE_TABLE_PROGRESSION_TASK)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(Tables.DROP_TABLE_PACKAGE)
        db.execSQL(Tables.DROP_TABLE_PARAM)
        db.execSQL(Tables.DROP_TABLE_STICKER)
        db.execSQL(Tables.DROP_TABLE_COLLECTION_TASK)
        db.execSQL(Tables.DROP_TABLE_PROGRESSION_TASK)
        onCreate(db)
    }


}

package com.eriks.android.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import com.eriks.core.objects.Param
import com.eriks.core.objects.ParamEnum
import com.eriks.core.repository.ParamRepository

class AndroidParamRepository(private val database: SQLiteDatabase): ParamRepository {

    override fun save(param: Param) {
        database.insertWithOnConflict("PARAM", null, serialize(param), CONFLICT_REPLACE)
    }

    override fun getParams(): List<Param> {
        val ret = mutableListOf<Param>()
        val cursor = database.query("PARAM", arrayOf("name", "value"), null, null, null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            ret.add(Param(ParamEnum.valueOf(cursor.getString(0)), cursor.getString(1)))
        }
        cursor?.close()
        return ret
    }

    private fun serialize(param: Param): ContentValues {
        val contentValues = ContentValues()
        contentValues.put("name", param.name.name)
        contentValues.put("value", param.value)
        return contentValues
    }
}
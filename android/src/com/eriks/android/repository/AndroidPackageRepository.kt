package com.eriks.android.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE
import com.eriks.core.objects.PackageOrigin
import com.eriks.core.objects.CardPackage
import com.eriks.core.repository.PackageRepository
import java.time.Instant

class AndroidPackageRepository(private val database: SQLiteDatabase): PackageRepository {

    override fun save(cardCardPackage: CardPackage) {
        database.insertWithOnConflict("PACKAGE", null, serialize(cardCardPackage), CONFLICT_IGNORE)
    }

    override fun openPackage(packageId: String) {
        database.execSQL("UPDATE PACKAGE SET is_open = '1' WHERE id = '$packageId'")
    }

    override fun getClosedPackages(): List<CardPackage> {
        val ret = mutableListOf<CardPackage>()
        val cursor = database.query("PACKAGE", arrayOf("id", "is_open", "origin", "timestamp"), "is_open is ?", arrayOf("0"), null, null, null)
        while (cursor != null && cursor.moveToNext()) {
            ret.add(
                CardPackage(
                    cursor.getString(0),
                    cursor.getString(1).toBoolean(),
                    PackageOrigin.valueOf(cursor.getString(2)),
                    Instant.ofEpochMilli(cursor.getLong(3))
                )
            )
        }
        cursor?.close()
        return ret
    }

    private fun serialize(cardCardPackage: CardPackage): ContentValues {
        val contentValue = ContentValues()
        contentValue.put("id", cardCardPackage.id)
        contentValue.put("is_open", cardCardPackage.isOpen)
        contentValue.put("origin", cardCardPackage.origin.name)
        contentValue.put("timestamp", cardCardPackage.date.toEpochMilli())
        return contentValue
    }

    override fun getPackagesById(packageIdList: List<String>): List<CardPackage> {
        val ret = mutableListOf<CardPackage>()
        val ids = packageIdList.joinToString(",") { "'$it'" }
        val cursor: Cursor = database.rawQuery("SELECT * FROM PACKAGE WHERE id IN ($ids)", null)
        while (cursor.moveToNext()) {
            ret.add(
                CardPackage(
                    cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("is_open")) > 0,
                    PackageOrigin.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("origin"))),
                    Instant.ofEpochMilli(cursor.getLong(cursor.getColumnIndexOrThrow("timestamp")))
                )
            )
        }
        cursor.close()
        return ret
    }
}

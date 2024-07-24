package com.eriks.desktop.db

import com.eriks.core.objects.PackageOrigin
import com.eriks.core.objects.CardPackage
import com.eriks.core.repository.PackageRepository
import java.time.Instant

class DesktopPackageRepository: PackageRepository {

    override fun save(cardCardPackage: CardPackage) {
        val prepareStatement = Database.conn.prepareStatement("INSERT OR IGNORE INTO PACKAGE (id, is_open, origin, timestamp) VALUES (?, ?, ?, ?)")
        prepareStatement.setString(1, cardCardPackage.id)
        prepareStatement.setBoolean(2, cardCardPackage.isOpen)
        prepareStatement.setString(3, cardCardPackage.origin.name)
        prepareStatement.setLong(4, cardCardPackage.date.toEpochMilli())
        prepareStatement.execute()
        prepareStatement.close()
    }

    override fun openPackage(packageId: String) {
        val prepareStatement = Database.conn.prepareStatement("UPDATE PACKAGE SET is_open = true WHERE id = ?")
        prepareStatement.setString(1, packageId)
        prepareStatement.execute()
        prepareStatement.close()
    }

    override fun getClosedPackages(): List<CardPackage> {
        val ret = mutableListOf<CardPackage>()
        val preparedStatement = Database.conn.prepareStatement("SELECT id, is_open, origin, timestamp FROM PACKAGE WHERE is_open is false")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(
                CardPackage(
                resultSet.getString("id"),
                resultSet.getBoolean("is_open"),
                PackageOrigin.valueOf(resultSet.getString("origin")),
                Instant.ofEpochMilli(resultSet.getLong("timestamp"))
            )
            )
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }

    override fun getPackagesById(packageIdList: List<String>): List<CardPackage> {
        val ret = mutableListOf<CardPackage>()
        val ids = packageIdList.joinToString(",") { "'$it'" }
        val preparedStatement = Database.conn.prepareStatement("SELECT * FROM PACKAGE WHERE id IN ($ids)")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(
                CardPackage(
                    resultSet.getString("id"),
                    resultSet.getBoolean("is_open"),
                    PackageOrigin.valueOf(resultSet.getString("origin")),
                    Instant.ofEpochMilli(resultSet.getLong("timestamp"))
                )
            )
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }
}
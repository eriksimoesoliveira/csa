package com.eriks.desktop.db

import com.eriks.core.objects.Param
import com.eriks.core.objects.ParamEnum
import com.eriks.core.repository.ParamRepository

class DesktopParamRepository: ParamRepository {

    override fun save(param: Param) {
        val prepareStatement = Database.conn.prepareStatement("INSERT OR REPLACE INTO PARAM (name, value) VALUES (?, ?)")
        prepareStatement.setString(1, param.name.name)
        prepareStatement.setString(2, param.value)
        prepareStatement.execute()
        prepareStatement.close()
    }

    override fun getParams(): List<Param> {
        val ret = mutableListOf<Param>()
        val preparedStatement = Database.conn.prepareStatement("SELECT name, value FROM PARAM")
        val resultSet = preparedStatement.executeQuery()
        while (resultSet.next()) {
            ret.add(Param(ParamEnum.valueOf(resultSet.getString("name")), resultSet.getString("value")))
        }
        resultSet.close()
        preparedStatement.close()
        return ret
    }
}
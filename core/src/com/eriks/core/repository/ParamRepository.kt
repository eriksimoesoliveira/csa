package com.eriks.core.repository

import com.eriks.core.objects.Param

interface ParamRepository {
    fun save(param: Param)
    fun getParams(): List<Param>
}
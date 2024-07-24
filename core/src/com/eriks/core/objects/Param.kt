package com.eriks.core.objects

class Param(val name: ParamEnum, val value: String) {

    companion object {
        fun increment(paramEnum: ParamEnum, params: Map<ParamEnum, String>): Param {
            var existingValue = (params[paramEnum] ?: "0").toInt()
            return Param(paramEnum, (++existingValue).toString())
        }
    }

}
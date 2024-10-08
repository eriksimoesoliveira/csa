package com.eriks.core.objects

import kotlinx.serialization.Serializable

@Serializable
class Ranking(var userName: String, var value: Double, var openPacks: Int, var totalPacks: Int)
package com.eriks.core.repository

import com.eriks.core.objects.CardPackage

interface PackageRepository {
    fun save(cardCardPackage: CardPackage)
    fun getClosedPackages(): List<CardPackage>
    fun openPackage(packageId: String)
    fun getPackagesById(packageIdList: List<String>): List<CardPackage>
}
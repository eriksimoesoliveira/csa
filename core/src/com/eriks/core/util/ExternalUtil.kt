package com.eriks.core.util

import com.badlogic.gdx.utils.Json
import com.eriks.core.GameController
import com.eriks.core.objects.PackageOrigin
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.logging.Level
import javax.net.ssl.HttpsURLConnection

object ExternalUtil {
    private val logger = LoggerConfig.getLogger()

    fun getPackageDispatch(playerName: String, packageIdsFound: (packagesIdsFound: List<String>) -> Unit) {
        Thread {
            try {
                val url = URL("https://raw.githubusercontent.com/eriksimoesoliveira/csa/main/package_dispatch.json?timestamp=${System.currentTimeMillis()}")
                val uc: HttpsURLConnection = url.openConnection() as HttpsURLConnection

                // Set headers to prevent caching
                uc.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
                uc.setRequestProperty("Pragma", "no-cache")
                uc.setRequestProperty("Expires", "0")

                val br = BufferedReader(InputStreamReader(uc.inputStream))
                val lin2 = StringBuilder()
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    lin2.append(line)
                }
                println("Getting packs for nickname $playerName")
                logger.info("Getting packs for nickname $playerName")
                println("Raw json: $lin2")
                logger.info("Raw json: $lin2")
                val packageDispatchDto = Json().fromJson(PackageDispatchDto::class.java, lin2.toString())
                println("Parsed json: ${packageDispatchDto.packList.size}")
                logger.info("Parsed json packlist size: ${packageDispatchDto.packList.size}")
                val packagesFound = filterUserPackage(packageDispatchDto, playerName)
                packageIdsFound(packagesFound)

            } catch (e: IOException) {
                e.printStackTrace()
                logger.log(Level.SEVERE, "Error getting packages", e)
            }
        }.start()
    }

    private fun filterUserPackage(packageDispatchDto: PackageDispatchDto, playerName: String): List<String> {
        return packageDispatchDto.packList.filter { it.players.contains(playerName) }.map { it.id }
    }

    fun getVersion(checkVersionCallBack: (version: String) -> Unit) {
        Thread {
            try {
                val url = URL("https://raw.githubusercontent.com/eriksimoesoliveira/csa/main/version.json?timestamp=${System.currentTimeMillis()}")
                val uc: HttpsURLConnection = url.openConnection() as HttpsURLConnection

                // Set headers to prevent caching
                uc.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate")
                uc.setRequestProperty("Pragma", "no-cache")
                uc.setRequestProperty("Expires", "0")

                val br = BufferedReader(InputStreamReader(uc.inputStream))
                val lin2 = StringBuilder()
                var line: String?

                while (br.readLine().also { line = it } != null) {
                    lin2.append(line)
                }
                println("Checking Version")
                logger.info("Checking Version")
                println("Raw json: $lin2")
                logger.info("Raw json: $lin2")
                val versionDto = Json().fromJson(VersionDto::class.java, lin2.toString())
                println("Parsed version: ${versionDto.version}")
                checkVersionCallBack(versionDto.version)

            } catch (e: IOException) {
                e.printStackTrace()
                logger.log(Level.SEVERE, "Error getting version", e)
            }
        }.start()
    }

}
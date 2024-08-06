package com.eriks.core.be

import com.eriks.core.be.dto.AlbumValueUpdate
import com.eriks.core.be.dto.LoginDto
import com.eriks.core.be.dto.LoginDtoOut
import com.eriks.core.be.dto.PackOpenDto
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BackendService {

    private const val baseUrl = "https://csabe-cb95c9877c4f.herokuapp.com/"
    private const val pw = "123test123"
    private lateinit var login: LoginDtoOut

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000  // Set request timeout to 60 seconds
            connectTimeoutMillis = 60_000  // Set connect timeout to 60 seconds
            socketTimeoutMillis = 60_000   // Set socket timeout to 60 seconds
        }
    }

    suspend fun login(userId: String, userName: String) {

        val response: HttpResponse = client.post("$baseUrl/csa/login") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(LoginDto(userId, userName, pw))
        }

        val responseBody = response.readText()
        login = Json.decodeFromString(responseBody)
    }

    suspend fun openPack(packOpenDto: PackOpenDto) {
        client.post<Unit>("$baseUrl/csa/metrics/pack-open") {
            contentType(ContentType.Application.Json)
            header("token", login.token)
            body = Json.encodeToString(packOpenDto)
        }
    }

    suspend fun albumValueUpdate(albumValueUpdate: AlbumValueUpdate) {

        client.put<Unit>("$baseUrl/csa/metrics/album-value") {
            contentType(ContentType.Application.Json)
            header("token", login.token)
            body = Json.encodeToString(albumValueUpdate)
        }
    }

    suspend fun ping(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = client.get<HttpResponse>("$baseUrl/csa/ping")
                response.status == HttpStatusCode.OK
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}

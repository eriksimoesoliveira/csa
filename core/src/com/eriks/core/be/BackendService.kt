package com.eriks.core.be

import com.eriks.core.be.dto.*
import com.eriks.core.objects.Ranking
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object BackendService {

    private const val baseUrl = "https://csabe-cb95c9877c4f.herokuapp.com"
//    private const val baseUrl = "http://localhost:8080"
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

    suspend fun login(userId: String, password: String) {
        val response: HttpResponse = client.post("$baseUrl/csa/login") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(LoginDto(userId, password))
        }

        val responseBody = response.readText()
        login = Json.decodeFromString(responseBody)
    }

    suspend fun signUp(userName: String, password: String): LoginDtoOut {
        val response: HttpResponse = client.post("$baseUrl/csa/sign-up") {
            contentType(ContentType.Application.Json)
            body = Json.encodeToString(SignUpDto(userName, password))
        }

        val responseBody = response.readText()
        login = Json.decodeFromString(responseBody)
        return login
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

    suspend fun getPackages(userName: String): List<OPAPackageDto> {
        val response: HttpResponse = client.get("$baseUrl/csa/fetch-packages/$userName") {
            contentType(ContentType.Application.Json)
            header("token", login.token)
        }

        val json = Json {
            ignoreUnknownKeys = true
        }

        val responseBody = response.readText()
        return json.decodeFromString(responseBody)
    }

    suspend fun getVersion(): List<String> {
        val response: HttpResponse = client.get("$baseUrl/csa/version") {
            contentType(ContentType.Application.Json)
        }

        val responseBody = response.readText()
        return Json.decodeFromString(responseBody)
    }

    suspend fun getRanking(): List<Ranking> {
        val response: HttpResponse = client.get("$baseUrl/csa/ranking") {
            contentType(ContentType.Application.Json)
            header("token", login.token)
        }

        val json = Json {
            ignoreUnknownKeys = true
        }

        val responseBody = response.readText()
        return json.decodeFromString(responseBody)
    }
}

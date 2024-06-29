package com.example.pokedexkmm_muia.data

import com.example.pokedexkmm_muia.initLogger
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class PokedexRepository {

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging){
            level = LogLevel.ALL
            logger = object: Logger {
                override fun log(message: String){
                    Napier.v(tag = "HttpClient", message = message)
                }
            }
            logger
        }
    }.also {
        initLogger()
    }

    suspend fun getPokedex(): Pokedex {
        val pokedex = httpClient.get("https://pokeapi.co/api/v2/pokemon/?limit=1025")
            .body<Pokedex>()
        return pokedex
    }
}
package com.example.pokedexkmm_muia

import app.cash.sqldelight.db.SqlDriver

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun initLogger()

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
package com.example.pokedexkmm_muia

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class IOSPlatform : Platform {
    override val name: String = "Device version error"
    //UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun initLogger() {
    Napier.base(DebugAntilog())
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PokedexDB.Schema, "pokedex.db")
    }
}

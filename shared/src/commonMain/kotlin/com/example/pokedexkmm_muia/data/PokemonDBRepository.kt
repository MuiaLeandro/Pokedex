package com.example.pokedexkmm_muia.data

import com.example.pokedexkmm_muia.DatabaseDriverFactory
import com.example.pokedexkmm_muia.PokedexDB

class PokemonDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = PokedexDB(databaseDriverFactory.createDriver())
    private val dbQuery = database.pokedexDBQueries

    fun clearDatabase() {
        dbQuery.deleteAllPokemons()
    }

    fun addPokemon(pokedex: Pokedex) {
        for (pokemon in pokedex.results) {
            dbQuery.insertPokemon(name = pokemon.name, url = pokemon.url)
        }
    }

    fun getAllPokemon(): List<PokedexResults> {
        return dbQuery.selectAllPokemon(::mapPokemonList).executeAsList()
    }

    private fun mapPokemonList(
        mapName: String,
        mapUrl: String
    ): PokedexResults {
        return PokedexResults(name = mapName, url = mapUrl)
    }
}
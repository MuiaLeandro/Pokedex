package com.example.pokedexkmm_muia.data

import kotlinx.serialization.Serializable

@Serializable
data class PokedexResults(
    val name: String,
    val url: String
)

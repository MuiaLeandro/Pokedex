package com.example.pokedexkmm_muia.data

import kotlinx.serialization.Serializable

@Serializable
data class Pokedex(
    val count: Int,
    val next: String,
    val results: List<PokedexResults>
)

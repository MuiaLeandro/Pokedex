package com.example.pokedexkmm_muia.android.ui

import com.example.pokedexkmm_muia.data.Pokedex

sealed class PokedexScreenState {
    object Loading : PokedexScreenState()

    object Error : PokedexScreenState()

    class ShowPokedex(val pokedex: Pokedex) : PokedexScreenState()

    class ShowPokedexFromCache(val pokedex: Pokedex) : PokedexScreenState()
}
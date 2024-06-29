package com.example.pokedexkmm_muia.android.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexkmm_muia.DatabaseDriverFactory
import com.example.pokedexkmm_muia.android.ui.viewModels.PokedexViewModel
import com.example.pokedexkmm_muia.data.PokedexRepository
import com.example.pokedexkmm_muia.data.PokemonDBRepository

class PokedexViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val pokedexRepository = PokedexRepository()
        val database = PokemonDBRepository(DatabaseDriverFactory(context))

        return PokedexViewModel(pokedexRepository, database) as T
    }
}
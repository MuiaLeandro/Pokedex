package com.example.pokedexkmm_muia.android.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexkmm_muia.android.ui.PokedexScreenState
import com.example.pokedexkmm_muia.data.Pokedex
import com.example.pokedexkmm_muia.data.PokedexRepository
import com.example.pokedexkmm_muia.data.PokemonDBRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokedexViewModel(
    private val pokedexRepository: PokedexRepository,
    val database: PokemonDBRepository
) : ViewModel
    () {

    val pokedex = MutableLiveData<Pokedex>()

    private val _screenState: MutableStateFlow<PokedexScreenState> = MutableStateFlow(
        PokedexScreenState.Loading
    )
    val screenState: Flow<PokedexScreenState> = _screenState

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d("PokedexViewModel", "Error retrieving pokedex: ${throwable.message}")
        }

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            kotlin.runCatching {
                pokedexRepository.getPokedex()
            }.onSuccess {
                if (it != null) {
                    pokedex.postValue(it)
                    _screenState.value = PokedexScreenState.ShowPokedex(it)

                    //agregar datos a la DB si no está completa
                    checkDataBaseContent(it)
                }
            }.recoverCatching {
                // cargar pokedex desde caché
                Log.d("PokedexViewModel", "Trying get pokedex from cache: ${it.message}")
                val pokedexResultsCache = database.getAllPokemon()
                if (pokedexResultsCache.isNotEmpty()) {
                    val pokedexCache = Pokedex(
                        1025,
                        "next",
                        pokedexResultsCache
                    )
                    pokedex.postValue(pokedexCache)
                    _screenState.value = PokedexScreenState.ShowPokedexFromCache(pokedexCache)
                } else {
                    _screenState.value = PokedexScreenState.Error
                }
            }.onFailure {
                Log.d("PokedexViewModel", "Error retrieving pokedex: ${it.message}")
                _screenState.value = PokedexScreenState.Error
            }
        }
    }

    private fun checkDataBaseContent(it: Pokedex) {
        val databasePokedexSize = database.getAllPokemon().size
        if (databasePokedexSize < 1025) {
            database.clearDatabase()
            database.addPokemon(it)
        }
    }
}

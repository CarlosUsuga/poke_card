package com.carlosusuga.pokemoncard.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.carlosusuga.pokemoncard.Entities.PokemonEntity
import com.carlosusuga.pokemoncard.Repository.PokeRepository

class PokeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PokeRepository

    val allPokemon: LiveData<List<PokemonEntity>>

    init {
        repository = PokeRepository(application)
        allPokemon = repository.allPokemon
    }

    fun insertPokemon(pokemonEntity: PokemonEntity){
        repository.insertPokemon(pokemonEntity)
    }

    fun updatePokemon(pokemonEntity: PokemonEntity){
        repository.updatePokemon(pokemonEntity)
    }

    fun deletePokemon(pokemonEntity: PokemonEntity){
        repository.deletePokemon(pokemonEntity)
    }

}
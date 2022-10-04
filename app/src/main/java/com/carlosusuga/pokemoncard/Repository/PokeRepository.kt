package com.carlosusuga.pokemoncard.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.carlosusuga.pokemoncard.DaosInterfaces.PokemonDaos
import com.carlosusuga.pokemoncard.DataBase.PokeDeskDataBase.Companion.getInstance
import com.carlosusuga.pokemoncard.Entities.PokemonEntity

class PokeRepository(application: Application?) {

    private val pokemonDaos: PokemonDaos

    val allPokemon: LiveData<List<PokemonEntity>>

    init {
        val database = getInstance(application!!)
        pokemonDaos = database!!.pokemonDaos()
        allPokemon = pokemonDaos.allPokemon()
    }

    fun insertPokemon(pokemonEntity: PokemonEntity){
        InsertPokemonAsyncTask(pokemonDaos).execute(pokemonEntity)
    }

    fun updatePokemon(pokemonEntity: PokemonEntity){
        UpdatePokemonAsyncTask(pokemonDaos).execute(pokemonEntity)
    }

    fun deletePokemon(pokemonEntity: PokemonEntity){
        DeleteProductoAsyncTask(pokemonDaos).execute(pokemonEntity)
    }

    private class InsertPokemonAsyncTask(private val pokemonDaos: PokemonDaos) :
        AsyncTask<PokemonEntity, Void?, Void?>() {

        override fun doInBackground(vararg pokemones: PokemonEntity): Void? {
            pokemonDaos.insertPokemon(pokemones[0])
            return null
        }
    }

    private class UpdatePokemonAsyncTask(private val pokemonDaos: PokemonDaos) :
            AsyncTask<PokemonEntity, Void?, Void?>(){
        override fun doInBackground(vararg pokemones: PokemonEntity): Void? {
            pokemonDaos.updatePokemon(pokemones[0])
            return null
        }
    }

    private class DeleteProductoAsyncTask(private val pokemonDaos: PokemonDaos) :
            AsyncTask<PokemonEntity, Void?, Void?>(){
        override fun doInBackground(vararg pokemones: PokemonEntity): Void? {
            pokemonDaos.deletePokemon(pokemones[0])
            return null
        }
    }
}
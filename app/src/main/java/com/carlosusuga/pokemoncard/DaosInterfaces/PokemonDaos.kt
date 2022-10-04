package com.carlosusuga.pokemoncard.DaosInterfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.carlosusuga.pokemoncard.Entities.PokemonEntity

@Dao
interface PokemonDaos {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemonEntity: PokemonEntity)

    @Update
    fun updatePokemon(pokemonEntity: PokemonEntity)

    @Delete
    fun deletePokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon_table ORDER BY nombrePokemon ASC")
    fun allPokemon(): LiveData<List<PokemonEntity>>
}
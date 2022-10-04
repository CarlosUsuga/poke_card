package com.carlosusuga.pokemoncard.App

import android.app.Application
import com.carlosusuga.pokemoncard.DataBase.PokeDeskDataBase
import com.carlosusuga.pokemoncard.ListAdapter.PokeAdapter
import com.carlosusuga.pokemoncard.Repository.PokeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PokeApplication : Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PokeDeskDataBase.getInstance(this) }
   // val repository by lazy { PokeRepository(database.pokemonDaos()) }
}
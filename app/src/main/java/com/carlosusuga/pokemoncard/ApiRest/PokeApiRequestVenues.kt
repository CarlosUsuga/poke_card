package com.carlosusuga.pokemoncard.ApiRest

import com.carlosusuga.pokemoncard.Querys.PokeApiClient
import kotlin.coroutines.coroutineContext

class PokeApiRequestVenues {

    var response: PokeResults? = null


}

class PokeResults{
    var results: ArrayList<Results>? = null
}

class Results{
    var id: String = ""
    var name: String = ""
    var url: String = ""
}

fun main(args: Array<String>) {
    val pokeApi = PokeApiClient()
    val bulbasaur = pokeApi.getPokemonSpecies(1)
//        println(bulbasaur)
}
package com.carlosusuga.pokemoncard.DaosInterfaces

import com.carlosusuga.pokemoncard.Entities.PokemonEntity

interface OnItemClickListener{
    fun onItemClick(pokemonEntity: PokemonEntity)
}
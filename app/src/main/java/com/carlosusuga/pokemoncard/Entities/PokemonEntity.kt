package com.carlosusuga.pokemoncard.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carlosusuga.pokemoncard.Entities.Abilities.Abilities
import java.net.URL

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "nombrePokemon")
    var name: String?,

    @ColumnInfo(name = "HabilidadPokemon")
    var ability: String?,

    @ColumnInfo(name = "MovimoentoPokemon")
    var MovimoentoPokemon: String?,

    @ColumnInfo(name = "imgPokemon")
    var imgPokemon: String?,

    @ColumnInfo(name = "url")
    var url: String?

)


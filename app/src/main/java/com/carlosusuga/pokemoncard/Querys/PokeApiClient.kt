package com.carlosusuga.pokemoncard.Querys

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.carlosusuga.pokemoncard.Activities.MainActivity
import com.carlosusuga.pokemoncard.ApiRest.main
import com.carlosusuga.pokemoncard.Connected.Network
import com.carlosusuga.pokemoncard.Entities.Pokemon
import com.google.gson.Gson

class PokeApiClient() {


    private val CODIGO_CONEXION = 200
    private val CODIGO_INTERCAMBIO_TOKEN = 201

    private val CLIENTE_ID = ""
    private val CLIENT_SECRET = ""

    private val SETTING = ""

    init {

    }
//
//    private fun validarActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
//        when(requestCode){
//            CODIGO_CONEXION->{
//                conexionCompleta(resultCode, data)
//            }
//            CODIGO_INTERCAMBIO_TOKEN->{
//                intercambioTokenCompleta(resultCode, data)
//            }
//        }
//    }
//
//    private fun conexionCompleta(requestCode: Int, data: Intent?){
//        val codigoRespuesta = .(requestCode,data)
//        val excepcion = codigoRespuesta.exception
//
//
//    }

    fun getPokemonSpecies(i: Int): Any {
 
        return true
    }
}
package com.carlosusuga.pokemoncard.Connected

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.carlosusuga.pokemoncard.DaosInterfaces.HttpResponse
import com.carlosusuga.pokemoncard.Mensajes.Errores
import com.carlosusuga.pokemoncard.Mensajes.Mensaje

class Network(var activity: AppCompatActivity) {

    fun redDisponible(): Boolean {
        val connetivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connetivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun cargarDatos(context: Context, url:String, httpResponse: HttpResponse){

        if (redDisponible()){

            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
                    response ->
                httpResponse.httpResponseSuccess(response)
            }, Response.ErrorListener {
                    error ->
                Log.d("HTTP_REQUEST", error.message!!)
                Mensaje.mensajeError(context, Errores.HTTP_ERROR)
            })
        } else {
            Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }
}
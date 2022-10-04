package com.carlosusuga.pokemoncard.Mensajes

import android.content.Context
import android.widget.Toast

class Mensaje {

    companion object{
        fun mensajesSuccess(){

        }

        fun mensajeError(context: Context, error: Errores){
            var mensaje = ""
            when (error){
                Errores.NO_HAY_RED->{
                    mensaje = "No hay una conexión disponible"
                }

                Errores.HTTP_ERROR->{
                    mensaje = "Hubo un problema en la descarga"
                }
            }

            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }
    }
}
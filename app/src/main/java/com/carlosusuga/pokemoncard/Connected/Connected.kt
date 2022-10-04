package com.carlosusuga.pokemoncard.Connected

import android.content.Context
import android.graphics.Bitmap
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.carlosusuga.pokemoncard.Activities.MainActivity

class Connected (context: Context){

    var cache: MainActivity? = null

    companion object{
        private var INSTANCE: Connected? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this){
            INSTANCE ?: Connected(context).also {
                INSTANCE = it
            }
        }
    }

    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue, object : ImageLoader.ImageCache{
            override fun getBitmap(url: String): Bitmap? {
                return cache?.get(url)
            }

            override fun putBitmap(url: String, bitmap: Bitmap?) {
                cache?.put(url, bitmap)
            }

        })
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }
    fun <T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req)
    }

}
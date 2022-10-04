package com.carlosusuga.pokemoncard.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.carlosusuga.pokemoncard.Activities.MainActivity.Companion.name
import com.carlosusuga.pokemoncard.R

class InitActivity : AppCompatActivity() {

    val TAG ="com.carlosusuga.pokemoncard.Activities.name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        Handler().postDelayed({
            val intent = Intent( this@InitActivity, MainActivity::class.java)
            intent.putExtra(TAG, name)
            startActivity(intent)
        }, 5000)
    }
}
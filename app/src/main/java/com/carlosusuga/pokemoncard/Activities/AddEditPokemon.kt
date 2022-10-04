package com.carlosusuga.pokemoncard.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.carlosusuga.pokemoncard.R
import com.google.android.material.appbar.MaterialToolbar

class AddEditPokemon : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = "com.carlosusuga.pokemoncard.Activities.EXTRA_ID"
        //falta imagen
        const val EXTRA_NAME_POKEMON = "com.carlosusuga.pokemoncard.Activities.EXTRA_NAME_POKEMON"
        const val EXTRA_HABILIDAD_POKEMON = "com.carlosusuga.pokemoncard.Activities.EXTRA_HABILIDAD_POKEMON"
        const val EXTRA_MOVIMIENTO_POKEMON = "com.carlosusuga.pokemoncard.Activities.EXTRA_MOVIMIENTO_POKEMON"
        const val EXTRA_IMG_POKEMON = "com.carlosusuga.pokemoncard.Activities.EXTRA_IMG_POKEMON"
    }

    private var editPokeNombre: EditText? = null
    private var editPokeHabilidad: EditText? = null
    private var editPokeMovimiento: EditText? = null
    private var imgPokemon: ImageView? = null

    private var btnPokeGuardar: Button? = null

    var toolbar: MaterialToolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_pokemon)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        editPokeNombre =  findViewById(R.id.edtNamePokemon)
        editPokeHabilidad = findViewById(R.id.edtHabilidad)
        editPokeMovimiento = findViewById(R.id.edtMovimientos)
        imgPokemon = findViewById(R.id.imgPokemon)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.backspace_reverse_outline)

        val intent = intent
        if (intent.hasExtra(EXTRA_ID)){
            title = "Editar Pokemon"
            editPokeNombre?.setText(intent.getStringExtra(EXTRA_NAME_POKEMON))
            editPokeHabilidad?.setText(intent.getStringExtra(EXTRA_HABILIDAD_POKEMON))
            editPokeMovimiento?.setText(intent.getStringExtra(EXTRA_MOVIMIENTO_POKEMON))
            imgPokemon?.setImageURI(intent.getParcelableExtra(EXTRA_IMG_POKEMON))
        } else {
            title = "Agregar Pokemon"
        }

        btnPokeGuardar = findViewById(R.id.btnAddPokemon)
        btnPokeGuardar?.setOnClickListener {
            savePokemon()
            Intent(this, MainActivity::class.java)
            finish()
        }
    }

    private fun savePokemon(){
        val pokeNombre = editPokeNombre!!.text.toString()
        val pokeHabilidad = editPokeHabilidad!!.text.toString()
        val pokeMovimiento = editPokeMovimiento!!.text.toString()
//        val pokeImg = imgPokemon!!.drawable

        if (pokeNombre.trim().isEmpty() || pokeHabilidad.trim().isEmpty() || pokeMovimiento.trim().isEmpty()){

            Toast.makeText(this, "Ingrese toda la información", Toast.LENGTH_LONG).show()

        } else {

            val data = Intent()
            data.putExtra(EXTRA_NAME_POKEMON, pokeNombre)
            data.putExtra(EXTRA_HABILIDAD_POKEMON, pokeHabilidad)
            data.putExtra(EXTRA_MOVIMIENTO_POKEMON, pokeMovimiento)
           // val pokeImg = imgPokemon!!.drawable

            val id = intent.getIntExtra(EXTRA_ID, -1)
            if (id != -1){
                data.putExtra(EXTRA_ID, id)
            }

            setResult(RESULT_OK, data)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_back, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.itemBack -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
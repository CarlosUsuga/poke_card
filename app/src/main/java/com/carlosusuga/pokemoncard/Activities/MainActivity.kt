package com.carlosusuga.pokemoncard.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.*
import com.carlosusuga.pokemoncard.Activities.AddEditPokemon.Companion.EXTRA_HABILIDAD_POKEMON
import com.carlosusuga.pokemoncard.Activities.AddEditPokemon.Companion.EXTRA_IMG_POKEMON
import com.carlosusuga.pokemoncard.Activities.AddEditPokemon.Companion.EXTRA_MOVIMIENTO_POKEMON
import com.carlosusuga.pokemoncard.Activities.AddEditPokemon.Companion.EXTRA_NAME_POKEMON
import com.carlosusuga.pokemoncard.Connected.Connected
import com.carlosusuga.pokemoncard.Connected.Network
import com.carlosusuga.pokemoncard.Entities.Abilities.Abilities
import com.carlosusuga.pokemoncard.Entities.Pokemon
import com.carlosusuga.pokemoncard.Entities.PokemonEntity
import com.carlosusuga.pokemoncard.ListAdapter.PokeAdapter
import com.carlosusuga.pokemoncard.R
import com.carlosusuga.pokemoncard.ViewModel.PokeViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var context:Context? = null

//    var cache = DiskBasedCache(cacheDir, 1024 * 1024)
//    val queue = Connected.getInstance(this.applicationContext).requestQueue

    val network = BasicNetwork(HurlStack())

    var url = "https://pokeapi.co/api/v2/pokemon/"
    var bitmap: Bitmap? = null


    private var pokeViewModel: PokeViewModel? = null
    val adapter = PokeAdapter()
    var toolbar: MaterialToolbar? = null

    var id: Int? = null
    var pokeNombre: TextView? = null
    var pokeHabilidad: TextView? = null
    var pokeMovimiento: TextView? = null
    var pokeImg: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        pokeNombre = findViewById(R.id.tvNamePokemon)

        val btnPokeAdd = findViewById<FloatingActionButton>(R.id.button_add_pokemon)
        btnPokeAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditPokemon::class.java)
            startActivityForResult(intent, ADD_POKEMON_REQUEST)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        pokeViewModel = ViewModelProvider(this@MainActivity).get(PokeViewModel::class.java)
        pokeViewModel!!.allPokemon.observe(this){ pokemones ->
            //Actualizar recyclerView
            adapter.submitList(pokemones)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                pokeViewModel!!.deletePokemon(adapter.getPokeAt(viewHolder.adapterPosition)!!)
                Toast.makeText(this@MainActivity, "Pokemon Eliminado", Toast.LENGTH_LONG).show()
            }

        }).attachToRecyclerView(recyclerView)

        adapter.setOnItemClickListener { pokemonEntity: PokemonEntity ->
            val intent = Intent(this@MainActivity, AddEditPokemon::class.java)
            intent.putExtra(AddEditPokemon.EXTRA_ID, pokemonEntity.id)
            intent.putExtra(EXTRA_NAME_POKEMON, pokemonEntity.name)
            intent.putExtra(EXTRA_HABILIDAD_POKEMON, pokemonEntity.ability)
            intent.putExtra(EXTRA_MOVIMIENTO_POKEMON, pokemonEntity.MovimoentoPokemon)
            intent.putExtra(EXTRA_IMG_POKEMON, pokemonEntity.imgPokemon)
            startActivityForResult(intent, EDIT_POKEMON_REQUEST)
        }

        //cache.getFileForKey(url)
        //queue.start()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_POKEMON_REQUEST && resultCode == RESULT_OK){

            id = data!!.getIntExtra(AddEditPokemon.EXTRA_ID, 0)
            val pokeNombre = data.getStringExtra(EXTRA_NAME_POKEMON)
            val pokeHabilidad = data.getStringExtra(EXTRA_HABILIDAD_POKEMON)
            val pokeMovimiento = data.getStringExtra(EXTRA_MOVIMIENTO_POKEMON)
            val pokeUrl = data.getStringExtra(EXTRA_IMG_POKEMON)

            val pokemon = PokemonEntity(id!!, pokeNombre.toString(), pokeHabilidad.toString(), pokeMovimiento.toString(), pokeImg.toString(), pokeUrl.toString())

            pokeViewModel!!.insertPokemon(pokemon)
            Toast.makeText(this, "Pokemon Guardado", Toast.LENGTH_LONG).show()

        } else if (requestCode == EDIT_POKEMON_REQUEST && resultCode == RESULT_OK){
            val id = data!!.getIntExtra(AddEditPokemon.EXTRA_ID, -1)

            if (id == -1){
                Toast.makeText(this, "No se puede Actualizar Pokemon", Toast.LENGTH_LONG).show()
                return
            }

            val pokeNombre = data.getStringExtra(EXTRA_NAME_POKEMON)
            val pokeHabilidad = data.getStringExtra(EXTRA_HABILIDAD_POKEMON)
            val pokeMovimiento = data.getStringExtra(EXTRA_MOVIMIENTO_POKEMON)
            val pokeUrl = data.getStringExtra(EXTRA_IMG_POKEMON)

            val pokemon = PokemonEntity(id, pokeNombre.toString(), pokeHabilidad.toString(), pokeMovimiento.toString(), pokeImg.toString(), pokeUrl.toString())

            pokemon.id = id

            pokeViewModel!!.updatePokemon(pokemon)

            Toast.makeText(this, "Pokemon Actualizado", Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this, "No se pudo guardar los datos", Toast.LENGTH_LONG).show()
        }
    }

    // Metodo para volley
    private fun solicitudHttpVolley(url:String){
        val queue = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, url, {
                response ->
            try {
                Log.d("solicitudHttpVolley", response)

                val gson = Gson()
                val pokedesk = gson.fromJson(response, Pokemon::class.java)
                pokeNombre?.text = pokedesk.name?.get(0)?.name

            }catch (e:Exception){

            }
        }, {

        })

        queue.add(solicitud)
    }

    fun get(url: String): Bitmap? {
        this.url = url
        return null
    }

    fun put(url: String, bitmap: Bitmap?): Bitmap?{
        this.url = url
        this.bitmap = bitmap
        return null
    }


    companion object{
        const val name = "name/"
        const val ADD_POKEMON_REQUEST = 1
        const val EDIT_POKEMON_REQUEST = 2
    }

}

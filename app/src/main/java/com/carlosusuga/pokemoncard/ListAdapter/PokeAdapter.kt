package com.carlosusuga.pokemoncard.ListAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carlosusuga.pokemoncard.DaosInterfaces.OnItemClickListener
import com.carlosusuga.pokemoncard.Entities.PokemonEntity
import com.carlosusuga.pokemoncard.R
import com.carlosusuga.pokemoncard.ListAdapter.PokeAdapter.PokeHolder
import kotlin.coroutines.coroutineContext

class PokeAdapter :  ListAdapter<PokemonEntity, PokeHolder> (DIFF_CALLBACK){

    private var listener: OnItemClickListener? = null
    //var context: Context
//    var pokePhoto : MutableList<PokemonEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokeHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokeHolder, position: Int) {
        val currentPokemon = getItem(position)
       // Glide.with(holder.itemView).load(currentPokemon.url).into(holder.imgPokeFoto)
        holder.tvPokeNombre.text = currentPokemon!!.name
    }

    fun getPokeAt(position: Int): PokemonEntity? {
        return getItem(position)
    }

    inner class PokeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPokeNombre: TextView
        // val tvPokeHabilidad: TextView
        // val tvPokeMovimiento: TextView
        val imgPokeFoto: ImageView

        init {
            tvPokeNombre = itemView.findViewById(R.id.tvNamePokemon)
            imgPokeFoto = itemView.findViewById(R.id.imgPokemon)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener!!.onItemClick(getItem(position))
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (PokemonEntity) -> Unit){
        listener
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PokemonEntity> =
            object : DiffUtil.ItemCallback<PokemonEntity>(){
                override fun areItemsTheSame(
                    oldItem: PokemonEntity,
                    newItem: PokemonEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PokemonEntity,
                    newItem: PokemonEntity
                ): Boolean {
                    return oldItem.name == newItem.name && oldItem.imgPokemon == newItem.imgPokemon
                }

            }
    }

}
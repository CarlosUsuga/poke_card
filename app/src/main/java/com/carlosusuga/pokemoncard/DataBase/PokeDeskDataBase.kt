package com.carlosusuga.pokemoncard.DataBase

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.carlosusuga.pokemoncard.DaosInterfaces.PokemonDaos
import com.carlosusuga.pokemoncard.Entities.PokemonEntity
import kotlinx.coroutines.CoroutineScope

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokeDeskDataBase : RoomDatabase(){

    abstract fun pokemonDaos(): PokemonDaos

    private class PopulateDbAsyncTask(db: PokeDeskDataBase?) : AsyncTask<Void, Void, Void>(){
        private val pokemonDaos: PokemonDaos
        override fun doInBackground(vararg voids: Void?): Void? {
//            pokemonDaos.insertPokemon(PokemonEntity(1, "Goku", "Kamekame ha", "solo recta", "https://cn.i.cdn.ti-platform.com/content/1149/pokemon-sol-y-luna-ultraleyendas/showpage/ve/showsquare.a6a9b623.png?imwidth=420", "https://pokeapi.co/api/v2/pokemon/"))
            return null
        }

        init {
            pokemonDaos = db!!.pokemonDaos()
        }
    }

    companion object {
        private var instance: PokeDeskDataBase? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context/*, scope: CoroutineScope*/): PokeDeskDataBase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokeDeskDataBase::class.java, "inventario_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}
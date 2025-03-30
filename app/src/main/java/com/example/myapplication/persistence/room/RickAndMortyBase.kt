package com.example.myapplication.persistence.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.persistence.dao.RickAndMortyDAO
import com.example.myapplication.persistence.models.RickAndMortyEntity

@Database(
    entities = [RickAndMortyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RickAndMortyBase : RoomDatabase() {

    abstract fun rickAndMortyDAO(): RickAndMortyDAO

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyBase? = null

        fun getDatabase(context: Context): RickAndMortyBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickAndMortyBase::class.java,
                    "rick_and_morty_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.example.myapplication.data.local.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.dao.RickAndMortyDAO
import com.example.myapplication.data.local.models.RickAndMortyEntity
import com.example.myapplication.utils.BBDDNames


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
                    BBDDNames.RICK_AND_MORTY_DB.name
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
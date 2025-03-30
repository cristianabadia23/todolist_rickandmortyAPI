package com.example.myapplication.persistence.room

import com.example.myapplication.persistence.models.TodoModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.constants.BBDDNames
import com.example.myapplication.persistence.dao.TodoDao

@Database(entities = [TodoModel::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoTaskDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context?): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    TodoDatabase::class.java,
                    BBDDNames.TODO_DB.name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.example.myapplication.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.TaskState

@Entity(tableName = "todotask")
data class TodoModel(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                     val title: String, val description: String,
                     val state: String= TaskState.IN_PROGRESS.name, val date: String,
                     val isChecked: Boolean = false){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TodoModel
        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        return state == other.state
    }
}
package com.example.myapplication.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.constants.TaskState

@Entity(tableName = "todotask")
data class TodoModel(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                     val title: String, val description: String,
                     val state: String= TaskState.IN_PROGRESS.name, val date: String,
                     val isChecked: Boolean = false)
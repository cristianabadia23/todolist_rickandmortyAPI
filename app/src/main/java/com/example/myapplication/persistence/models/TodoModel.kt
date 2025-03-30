package com.example.myapplication.persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todotask")
data class TodoModel(@PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String, val description: String,
    val state: String="in progress", val date: String,
    val isChecked: Boolean = false)
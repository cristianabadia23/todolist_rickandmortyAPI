package com.example.myapplication.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RickAndMortyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val status: String,
    val isFavorite: Boolean
)
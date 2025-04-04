package com.example.myapplication.data.local.models

data class RickAndMorty(val id: Int, val name: String, val image: String, val species: String, val status: String, var isFavorite: Boolean)

data class RickAndMortyResponse(val results: List<RickAndMorty>)
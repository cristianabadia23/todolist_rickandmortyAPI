package com.example.myapplication.api

import com.example.myapplication.persistence.models.RickAndMortyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): RickAndMortyResponse
}
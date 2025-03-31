package com.example.myapplication.ui.rickandmortyfav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.persistence.models.RickAndMortyEntity
import com.example.myapplication.persistence.repository.RickAndMortyRepository

class RickAndMortyFAVViewModel(private val repository: RickAndMortyRepository) : ViewModel() {
    val favoriteCharacters: LiveData<List<RickAndMortyEntity>> = repository.getFavoriteCharacters()
}
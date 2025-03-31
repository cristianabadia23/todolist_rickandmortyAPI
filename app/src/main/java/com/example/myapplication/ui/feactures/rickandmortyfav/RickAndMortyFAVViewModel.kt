package com.example.myapplication.ui.feactures.rickandmortyfav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.models.RickAndMortyEntity
import com.example.myapplication.data.repository.RickAndMortyRepository

class RickAndMortyFAVViewModel(private val repository: RickAndMortyRepository) : ViewModel() {
    val favoriteCharacters: LiveData<List<RickAndMortyEntity>> = repository.getFavoriteCharacters()
}
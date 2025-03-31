package com.example.myapplication.ui.feactures.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.RickAndMortyRepository
import com.example.myapplication.ui.feactures.rickAndMorty.RickAndMortyViewModel

class RickAndMortyViewModelFactory(private val repository: RickAndMortyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RickAndMortyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RickAndMortyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

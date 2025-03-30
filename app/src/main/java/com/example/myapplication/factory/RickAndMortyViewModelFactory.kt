package com.example.myapplication.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.persistence.room.RickAndMortyBase
import com.example.myapplication.ui.RickAndMorty.RickAndMortyViewModel

class RickAndMortyViewModelFactory(private val db: RickAndMortyBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RickAndMortyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RickAndMortyViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.myapplication.ui.feactures.rickAndMorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.api.RickAndMortyApi
import com.example.myapplication.data.local.models.RickAndMorty
import com.example.myapplication.data.local.models.RickAndMortyEntity
import com.example.myapplication.data.repository.RickAndMortyRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyViewModel(private val repository: RickAndMortyRepository) : ViewModel() {
    private val _characters = MutableLiveData<List<RickAndMorty>>()
    val characters: LiveData<List<RickAndMorty>> get() = _characters
    private var currentPage = 1
    private var isLoading = false

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RickAndMortyApi::class.java)

    fun fetchCharacters() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val cachedCharacters = repository.allCharacters.value
            if (cachedCharacters != null && cachedCharacters.isNotEmpty() && currentPage == 1) {
                _characters.value = cachedCharacters.map { entity ->
                    RickAndMorty(entity.id, entity.name, entity.image, entity.species, entity.status, entity.isFavorite)
                }
            } else {
                try {
                    val response = retrofit.getCharacters(currentPage)
                    val newCharacters = response.results
                    _characters.value = (_characters.value ?: emptyList()) + newCharacters
                    repository.insertAllCharacters(newCharacters.map { char ->
                        RickAndMortyEntity(char.id, char.name, char.image, char.species, char.status, char.isFavorite)
                    })
                    currentPage++
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun toggleFavorite(character: RickAndMorty) {
        character.isFavorite = !character.isFavorite
        viewModelScope.launch {
            val updatedEntity = RickAndMortyEntity(character.id, character.name, character.image, character.species, character.status, character.isFavorite)
            repository.updateCharacter(updatedEntity)
            _characters.value = _characters.value?.map {
                if (it.id == character.id) character else it
            }
        }
    }
}

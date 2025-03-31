package com.example.myapplication.persistence.repository


import com.example.myapplication.persistence.dao.RickAndMortyDAO
import com.example.myapplication.persistence.models.RickAndMortyEntity

class RickAndMortyRepository(private val rickAndMortyDAO: RickAndMortyDAO) {

    val allCharacters = rickAndMortyDAO.getAllCharacters()

    suspend fun insertCharacter(character: RickAndMortyEntity) {
        rickAndMortyDAO.insertCharacter(character)
    }

    suspend fun insertAllCharacters(characters: List<RickAndMortyEntity>) {
        rickAndMortyDAO.insertAllCharacters(characters)
    }

    fun getCharacterById(characterId: Int) = rickAndMortyDAO.getCharacterById(characterId)

    suspend fun updateCharacter(character: RickAndMortyEntity) {
        rickAndMortyDAO.updateCharacter(character)
    }

    suspend fun deleteCharacter(characterId: Int) {
        rickAndMortyDAO.deleteCharacter(characterId)
    }

    suspend fun deleteAllCharacters() {
        rickAndMortyDAO.deleteAllCharacters()
    }

    fun getFavoriteCharacters() = rickAndMortyDAO.getFavoriteCharacters()

}
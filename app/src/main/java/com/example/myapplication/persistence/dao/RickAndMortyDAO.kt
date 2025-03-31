package com.example.myapplication.persistence.dao



import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.persistence.models.RickAndMortyEntity

@Dao
interface RickAndMortyDAO {

    @Query("SELECT * FROM RickAndMortyEntity")
    fun getAllCharacters(): LiveData<List<RickAndMortyEntity>>

    @Query("SELECT * FROM RickAndMortyEntity WHERE id = :characterId")
    fun getCharacterById(characterId: Int): LiveData<RickAndMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: RickAndMortyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<RickAndMortyEntity>)

    @Update
    suspend fun updateCharacter(character: RickAndMortyEntity)

    @Query("DELETE FROM RickAndMortyEntity WHERE id = :characterId")
    suspend fun deleteCharacter(characterId: Int)

    @Query("DELETE FROM RickAndMortyEntity")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM RickAndMortyEntity WHERE isFavorite = 1")
    fun getFavoriteCharacters(): LiveData<List<RickAndMortyEntity>>
}
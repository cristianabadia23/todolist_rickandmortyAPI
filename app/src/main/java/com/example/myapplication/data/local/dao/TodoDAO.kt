package com.example.myapplication.data.local.dao

import androidx.room.*
import com.example.myapplication.data.local.models.TodoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoModel)

    @Update
    suspend fun updateTask(task: TodoModel)

    @Delete
    suspend fun deleteTask(task: TodoModel)

    @Query("SELECT * FROM todotask ORDER BY date DESC")
    fun getAllTasks(): Flow<List<TodoModel>>

    @Query("SELECT * FROM todotask WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Int): TodoModel?
}

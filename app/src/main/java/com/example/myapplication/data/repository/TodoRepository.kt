package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.TodoDao
import com.example.myapplication.data.local.models.TodoModel
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoTaskDao: TodoDao) {

    val allTasks: Flow<List<TodoModel>> = todoTaskDao.getAllTasks()

    suspend fun insert(task: TodoModel) {
        todoTaskDao.insertTask(task)
    }

    suspend fun update(task: TodoModel) {
        todoTaskDao.updateTask(task)
    }

    suspend fun delete(task: TodoModel) {
        todoTaskDao.deleteTask(task)
    }

    suspend fun getTaskById(id: Int): TodoModel? {
        return todoTaskDao.getTaskById(id)
    }
}

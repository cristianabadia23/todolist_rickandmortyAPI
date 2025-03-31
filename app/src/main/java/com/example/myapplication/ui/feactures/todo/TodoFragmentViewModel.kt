package com.example.myapplication.ui.feactures.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.models.TodoModel
import com.example.myapplication.data.repository.TodoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow

class TodoFragmentViewModel(private val repository: TodoRepository) : ViewModel() {

    val allTasks: Flow<List<TodoModel>> = repository.allTasks

    fun updateTask(task: TodoModel) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

}
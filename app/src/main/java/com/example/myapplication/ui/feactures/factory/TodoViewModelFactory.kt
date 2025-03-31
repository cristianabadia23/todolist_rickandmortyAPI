package com.example.myapplication.ui.feactures.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.TodoRepository
import com.example.myapplication.ui.feactures.todo.TodoFragmentViewModel

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

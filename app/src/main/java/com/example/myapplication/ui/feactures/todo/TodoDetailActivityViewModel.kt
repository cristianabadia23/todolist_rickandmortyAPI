package com.example.myapplication.ui.feactures.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.models.TodoModel
import com.example.myapplication.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoDetailActivityViewModel(private val repository: TodoRepository) : ViewModel()  {

    fun updateTask(task: TodoModel) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun deleteTask(task: TodoModel) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun getTaskById(id: Int, callback: (TodoModel?) -> Unit) {
        viewModelScope.launch {
            callback(repository.getTaskById(id))
        }
    }

    fun insertTask(todoModel: TodoModel){
        viewModelScope.launch {
            repository.insert(todoModel)
        }
    }
}
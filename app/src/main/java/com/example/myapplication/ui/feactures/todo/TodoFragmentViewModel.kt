package com.example.myapplication.ui.feactures.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.models.TodoModel
import com.example.myapplication.data.repository.TodoRepository
import com.example.myapplication.utils.TaskState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TodoFragmentViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _allTasks = repository.allTasks
    val allTasks: Flow<List<TodoModel>> = _allTasks

    fun getCompletedTasks(): Flow<List<TodoModel>> {
        return _allTasks.map { tasks ->
            tasks.filter { it.state == TaskState.COMPLETED.name }
        }
    }

    fun getInProgressTasks(): Flow<List<TodoModel>> {
        return _allTasks.map { tasks ->
            tasks.filter { it.state == TaskState.IN_PROGRESS.name }
        }
    }

    fun getDeletedTasks(): Flow<List<TodoModel>> {
        return _allTasks.map { tasks ->
            tasks.filter { it.state == TaskState.DELETED.name }
        }
    }

    fun updateTask(task: TodoModel) {
        viewModelScope.launch {
            repository.update(task)
        }
    }
}
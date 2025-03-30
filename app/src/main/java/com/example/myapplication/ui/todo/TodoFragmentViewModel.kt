package com.example.myapplication.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.constants.TaskState
import com.example.myapplication.persistence.models.TodoModel
import com.example.myapplication.persistence.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoFragmentViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun setFilter(filter: String) {
        _uiState.update { currentState ->
            currentState.copy(currentFilter = filter)
        }
        loadTasks()
    }

    fun updateTask(task: TodoModel) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.allTasks.collect { allTasks ->
                _uiState.update { currentState ->
                    currentState.copy(
                        tasks = when (currentState.currentFilter) {
                            TaskState.ALL.name -> allTasks
                            TaskState.IN_PROGRESS.name ->
                                allTasks.filter { it.state == TaskState.IN_PROGRESS.name }
                            TaskState.COMPLETED.name ->
                                allTasks.filter { it.state == TaskState.COMPLETED.name }
                            TaskState.DELETED.name ->
                                allTasks.filter { it.state == TaskState.DELETED.name }
                            else -> allTasks
                        },
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addNewTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(
                TodoModel(
                    title = title,
                    description = description,
                    state = TaskState.IN_PROGRESS.name,
                    date = System.currentTimeMillis().toString()
                )
            )
        }
    }
}

data class TodoUiState(
    val tasks: List<TodoModel> = emptyList(),
    val currentFilter: String = TaskState.ALL.name,
    val isLoading: Boolean = true
)
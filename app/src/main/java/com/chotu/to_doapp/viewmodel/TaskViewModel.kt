package com.chotu.to_doapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chotu.to_doapp.data.entity.TaskEntity
import com.chotu.to_doapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    var tasks by mutableStateOf<List<TaskEntity>>(emptyList())
        private set

    fun loadTasks() {
        viewModelScope.launch {
            tasks = repository.getAllTasks()
        }
    }

    fun insertTask(title: String) {
        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title
                )
            )
            loadTasks()
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks()
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks()
        }
    }
}
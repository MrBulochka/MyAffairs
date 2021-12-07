package com.notacompany.myaffairs.ui.task_card

import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.data.repository.DataRepository
import com.notacompany.myaffairs.ui.projects.ProjectViewModel
import kotlinx.coroutines.launch

class TaskCardViewModel(private val repository: DataRepository): ViewModel() {

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }
}

class TaskCardViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
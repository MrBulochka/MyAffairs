package com.notacompany.myaffairs.ui.project_card

import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectCardViewModel(private val repository: DataRepository): ViewModel() {

    var projectTasks: LiveData<List<Task>>? = null

    fun getProjectTasks(project: Project) {
        viewModelScope.launch {
            projectTasks = repository.getProjectTasks(project.id).asLiveData()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun updateTaskOrder(tasks: List<Task>) {
        viewModelScope.launch {
            repository.updateTaskOrder(tasks)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            repository.deleteProject(project)
        }
    }

    fun deleteProjectTasks(id: Long?) {
        viewModelScope.launch {
            repository.deleteProjectTasks(id)
        }
    }
}

class ProjectCardViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectCardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectCardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
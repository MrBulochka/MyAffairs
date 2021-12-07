package com.notacompany.myaffairs.ui.projects

import android.util.Log
import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: DataRepository): ViewModel() {

    val allProject: LiveData<List<Project>> = repository.allProjects.asLiveData()

    fun updateProjectOrder(projects: List<Project>) {
        viewModelScope.launch {
            repository.updateProjectOrder(projects)
        }
    }
}

class ProjectViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
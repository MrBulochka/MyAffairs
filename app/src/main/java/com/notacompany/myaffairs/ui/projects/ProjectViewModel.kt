package com.notacompany.myaffairs.ui.projects

import android.util.Log
import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: DataRepository): ViewModel() {

    val allProject: LiveData<List<Project>> = repository.allProjects.asLiveData()

    private lateinit var project: Project

    fun delete(project: Project) {
        viewModelScope.launch {
            repository.delete(project)
        }
    }

    fun update(project: Project) {
        viewModelScope.launch {
            repository.update(project)
        }
    }

    fun setProject(project: Project) {
        this.project = project
    }

    fun getProject() = project

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
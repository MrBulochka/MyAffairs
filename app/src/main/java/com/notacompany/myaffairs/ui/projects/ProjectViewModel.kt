package com.notacompany.myaffairs.ui.projects

import android.util.Log
import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: DataRepository): ViewModel() {

    val allProject: LiveData<List<Project>> = repository.allProjects.asLiveData()

//    var project = MutableLiveData<Project>()
    private lateinit var project: Project

    fun insert(project: Project) {
        viewModelScope.launch {
            repository.insert(project)
        }
    }

    fun delete(project: Project) {
        viewModelScope.launch {
            repository.delete(project)
        }
    }

    fun setProject(project: Project) {
        this.project = project
        Log.d("my log", "set project - $project")
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
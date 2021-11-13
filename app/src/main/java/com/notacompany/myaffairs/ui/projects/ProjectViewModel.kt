package com.notacompany.myaffairs.ui.projects

import android.util.Log
import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: DataRepository): ViewModel() {

    val allProject: LiveData<List<Project>> = repository.allProjects.asLiveData()
    var projectTasks = MutableLiveData<List<Task>>()

    private lateinit var project: Project

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            repository.deleteProject(project)
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

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun deleteProjectTasks(id: Long?) {
        viewModelScope.launch {
            repository.deleteProjectTasks(id)
        }
    }

    fun getProjectTasks(id: Long?) {
        viewModelScope.launch {
            projectTasks.value = repository.getProjectTasks(id)
            val tasks = projectTasks.value
            Log.d("My TAG", "liveData \n $tasks")
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
package com.notacompany.myaffairs.ui.projects

import android.util.Log
import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: DataRepository): ViewModel() {

    val allProject: LiveData<List<Project>> = repository.allProjects.asLiveData()

    val projectTasks: LiveData<List<Task>>? get() = _projectTasks
    private var _projectTasks: LiveData<List<Task>>? = null

    private lateinit var project: Project

    private var taskPosition = 0

//    set(value) {
//        _projectTasks = repository.getProjectTasks(value.id).asLiveData()
//        field = value
//    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            repository.deleteProject(project)
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            repository.update(project)
        }
    }

    fun updateProjectOrder(projects: List<Project>) {
        viewModelScope.launch {
            repository.updateProjectOrder(projects)
        }
    }

    fun setProject(project: Project) {
        this.project = project
        _projectTasks = repository.getProjectTasks(project.id).asLiveData()
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

    fun deleteProjectTasks(id: Long?) {
        viewModelScope.launch {
            repository.deleteProjectTasks(id)
        }
    }

    fun setTaskPosition(position: Int) {
        this.taskPosition = position
    }

    fun getTaskPosition(): Int {
        return taskPosition
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
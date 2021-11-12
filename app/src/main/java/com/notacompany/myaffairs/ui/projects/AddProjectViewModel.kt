package com.notacompany.myaffairs.ui.projects

import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class AddProjectViewModel(private val repository: DataRepository): ViewModel() {

    fun insertProject(project: Project) {
        viewModelScope.launch {
            repository.insertProject(project)
        }
    }
}

class AddProjectViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddProjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.notacompany.myaffairs.ui.edit_project

import androidx.lifecycle.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class EditProjectViewModel(private val repository: DataRepository): ViewModel() {

    fun updateProject(project: Project) {
        viewModelScope.launch {
            repository.update(project)
        }
    }
}

class EditProjectViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProjectViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
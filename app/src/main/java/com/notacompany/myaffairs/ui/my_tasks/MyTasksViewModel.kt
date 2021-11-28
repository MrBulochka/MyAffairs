package com.notacompany.myaffairs.ui.my_tasks

import androidx.lifecycle.*
import com.google.android.gms.tasks.Tasks
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.data.repository.DataRepository
import kotlinx.coroutines.launch

class MyTasksViewModel(private val repository: DataRepository): ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

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
}

class MyTasksViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTasksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyTasksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
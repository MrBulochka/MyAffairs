package com.notacompany.myaffairs.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.notacompany.myaffairs.data.model.Project

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<Project>()
    val taskPosition = MutableLiveData<Int>()

    fun select(project: Project) {
        selected.value = project
    }

    fun setTaskPosition(position: Int){
        taskPosition.value = position
    }
}
package com.notacompany.myaffairs.data.repository

import android.util.Log
import com.notacompany.myaffairs.data.db.ProjectDao
import com.notacompany.myaffairs.data.db.TaskDao
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import kotlinx.coroutines.flow.Flow

class DataRepository(private val projectDao: ProjectDao,
                     private val taskDao: TaskDao) {

    val allProjects: Flow<List<Project>> = projectDao.getProjects()

    suspend fun insertProject(project: Project) {
        projectDao.insert(project)
    }

    suspend fun update(project: Project) {
        projectDao.update(project)
    }

    suspend fun deleteProject(project: Project) {
        projectDao.delete(project)
    }

    suspend fun getProjectTasks(id: Long?): List<Task> {
        return taskDao.getProjectTasks(id)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun deleteProjectTasks(id: Long?) {
        taskDao.deleteProjectTasks(id)
    }
}
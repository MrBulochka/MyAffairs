package com.notacompany.myaffairs.data.repository

import com.notacompany.myaffairs.data.db.ProjectDao
import com.notacompany.myaffairs.data.model.Project
import kotlinx.coroutines.flow.Flow

class DataRepository(private val projectDao: ProjectDao) {

    val allProjects: Flow<List<Project>> = projectDao.getProjects()

    suspend fun insert(project: Project) {
        projectDao.insert(project)
    }

    suspend fun delete(project: Project) {
        projectDao.delete(project)
    }
}
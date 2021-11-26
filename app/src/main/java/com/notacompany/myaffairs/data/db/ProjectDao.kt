package com.notacompany.myaffairs.data.db

import androidx.room.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.ProjectsWithTasks
import com.notacompany.myaffairs.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects_table ORDER BY position")
    fun getProjects(): Flow<List<Project>>

    @Insert
    suspend fun insert(project: Project)

    @Update
    suspend fun update(project: Project)

    @Update
    suspend fun updateProjectOrder(projects: List<Project>)

    @Delete
    suspend fun delete(project: Project)
}

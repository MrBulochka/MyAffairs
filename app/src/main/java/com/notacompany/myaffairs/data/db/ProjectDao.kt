package com.notacompany.myaffairs.data.db

import androidx.room.*
import com.notacompany.myaffairs.data.model.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects_table")
    fun getProjects(): Flow<List<Project>>

    @Insert
    suspend fun insert(project: Project)

    @Delete
    suspend fun delete(project: Project)
}

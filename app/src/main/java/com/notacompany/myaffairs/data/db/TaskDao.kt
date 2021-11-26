package com.notacompany.myaffairs.data.db

import androidx.room.*
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks_table")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE project_id == :projectId ORDER BY position ")
    fun getProjectTasks(projectId: Long?): Flow<List<Task>>

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Update
    suspend fun updateTaskOrder(tasks: List<Task>)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM tasks_table WHERE project_id == :projectId")
    suspend fun deleteProjectTasks(projectId: Long?)
}
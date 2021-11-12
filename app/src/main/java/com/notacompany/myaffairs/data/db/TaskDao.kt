package com.notacompany.myaffairs.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks_table")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE project_id == :projectId")
    suspend fun getProjectTasks(projectId: Long?): List<Task>

    @Insert
    suspend fun insert(task: Task)
}
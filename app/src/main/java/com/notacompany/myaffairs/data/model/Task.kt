package com.notacompany.myaffairs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.Date

@Entity(tableName = "tasks_table")
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,

    val name: String,

    val deadline: Long,

    var complete: Boolean,

    @ColumnInfo(name = "project_id")
    val projectId: Long?,

    var position: Int = 0
)
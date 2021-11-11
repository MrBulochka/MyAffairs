package com.notacompany.myaffairs.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "projects_table")
data class Project (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,

    val title: String,

    val description: String,

    val deadline: String
)

//data class ProjectsWithTasks(
//    @Embedded val project: Project,
//    @Relation(
//        parentColumn = "Id",
//        entityColumn = "taskId"
//    )
//    val tasks: List<Task>
//)
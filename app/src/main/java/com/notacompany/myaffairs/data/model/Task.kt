package com.notacompany.myaffairs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    val taskId: Long? = null,
    val name: String,
    val deadline: String
)
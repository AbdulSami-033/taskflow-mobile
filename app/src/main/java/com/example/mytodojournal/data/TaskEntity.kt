package com.example.mytodojournal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val category: String,
    val priority: String,
    val dueDate: String,
    val isCompleted: Boolean = false
)
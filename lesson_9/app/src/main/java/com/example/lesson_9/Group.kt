package com.example.lesson_9

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "group")
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupName: String
)


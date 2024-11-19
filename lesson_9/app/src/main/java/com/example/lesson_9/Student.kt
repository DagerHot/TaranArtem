package com.example.lesson_9

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val surname: String,
    val age: Int,
    val groupId: Int // Зовнішній ключ
)


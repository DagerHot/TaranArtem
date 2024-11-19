package com.example.lesson_9

import androidx.room.Embedded
import androidx.room.Relation

data class StudentWithGroup(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "id"
    )
    val group: Group
)

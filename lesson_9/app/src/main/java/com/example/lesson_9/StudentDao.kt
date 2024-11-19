package com.example.lesson_9

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)

    // Додайте метод для вставки списку студентів
    @Insert
    suspend fun insertStudents(students: List<Student>)

    @Query("SELECT * FROM student")
    suspend fun getAllStudents(): List<Student>

    @Transaction
    @Query("SELECT * FROM student")
    suspend fun getStudentsWithGroupInfo(): List<StudentWithGroup>
}



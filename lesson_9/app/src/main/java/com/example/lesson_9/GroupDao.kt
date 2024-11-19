package com.example.lesson_9

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GroupDao {
    @Insert
    suspend fun insert(group: Group)

    @Query("SELECT * FROM `group` WHERE id = :id")  // Використовуйте зворотні лапки для захисту ключових слів
    suspend fun getGroupById(id: Int): Group?

    @Query("SELECT * FROM `group`")
    suspend fun getAllGroups(): List<Group>

    @Insert
    suspend fun insertGroup(groups: List<Group>)

}

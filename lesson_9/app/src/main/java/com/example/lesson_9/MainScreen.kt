package com.example.lesson_9

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Column {
        Button(onClick = { navController.navigate("createGroup") }) {
            Text("Створити групу")
        }
        Button(onClick = { navController.navigate("addStudent") }) {
            Text("Додати студента")
        }
        Button(onClick = { navController.navigate("viewStudents") }) {
            Text("Показати студентів")
        }
    }
}

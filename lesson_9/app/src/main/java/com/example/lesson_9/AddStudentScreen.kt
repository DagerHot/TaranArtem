package com.example.lesson_9

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lesson_9.Group
import com.example.lesson_9.Student
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp

@Composable
fun AddStudentScreen(studentViewModel: StudentViewModel, groupViewModel: GroupViewModel) {
    // Тепер можна використовувати collectAsState з StateFlow
    val groupsState = groupViewModel.groups.collectAsState()
    val groups = groupsState.value

    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val selectedGroup = remember { mutableStateOf<Group?>(null) }
    val expanded = remember { mutableStateOf(false) }

    Column {
        TextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Ім'я") })
        TextField(value = surname.value, onValueChange = { surname.value = it }, label = { Text("Прізвище") })
        TextField(value = age.value, onValueChange = { age.value = it }, label = { Text("Вік") })

        Button(onClick = { expanded.value = !expanded.value }) {
            Text("Оберіть групу")
        }

        // Без DropdownMenuItem, створимо свій власний список елементів
        if (expanded.value) {
            Column {
                groups.forEach { group ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedGroup.value = group
                                expanded.value = false  // Закриваємо меню після вибору
                            }
                            .padding(8.dp)
                    ) {
                        Text(text = group.groupName)
                    }
                }
            }
        }

        Button(onClick = {
            selectedGroup.value?.let {
                studentViewModel.addStudent(
                    Student(
                        name = name.value,
                        surname = surname.value,
                        age = age.value.toInt(),
                        groupId = it.id
                    )
                )
            }
        }) {
            Text("Додати студента")
        }
    }
}

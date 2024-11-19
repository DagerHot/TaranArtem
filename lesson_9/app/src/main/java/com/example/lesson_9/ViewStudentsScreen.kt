package com.example.lesson_9

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items



@Composable
fun ViewStudentsScreen(viewModel: StudentViewModel) {
    val studentsWithGroup = viewModel.studentsWithGroup.collectAsState().value

    LazyColumn {
        items(studentsWithGroup) { studentWithGroup ->
            Text(
                text = "${studentWithGroup.student.name} ${studentWithGroup.student.surname}, " +
                        "${studentWithGroup.student.age} років, " +
                        "Група: ${studentWithGroup.group.groupName}"
            )
        }
    }
}





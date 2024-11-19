package com.example.lesson_9

import com.example.lesson_9.Group
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CreateGroupScreen(viewModel: GroupViewModel) {
    var groupName by remember { mutableStateOf("") }

    Column {
        TextField(
            value = groupName,
            onValueChange = { groupName = it },
            label = { Text("Назва групи") }
        )
        Button(onClick = {
            if (groupName.isNotEmpty()) {
                viewModel.addGroup(Group(groupName = groupName))
            }
        }) {
            Text("Додати групу")
        }
    }
}

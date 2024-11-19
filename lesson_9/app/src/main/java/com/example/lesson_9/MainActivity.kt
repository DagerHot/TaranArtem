package com.example.lesson_9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.lesson_9.ui.theme.Lesson_9Theme

class MainActivity : ComponentActivity() {
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(AppDatabase.getDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lesson_9Theme {
                ViewStudentsScreen(viewModel = studentViewModel)
            }
        }
    }
}




package com.example.lesson_9

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupViewModel(private val database: AppDatabase) : ViewModel() {
    // Перетворюємо на StateFlow для інтеграції з Compose
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups

    init {
        // Завантажуємо групи асинхронно при ініціалізації ViewModel
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            _groups.value = database.groupDao().getAllGroups()  // Викликаємо suspend функцію в корутині
        }
    }

    fun addGroup(group: Group) {
        viewModelScope.launch {
            database.groupDao().insert(group)
            loadGroups()  // Перезавантажуємо список після додавання нової групи
        }
    }
}


// ViewModel
class StudentViewModel(private val database: AppDatabase) : ViewModel() {
    private val _studentsWithGroup = MutableStateFlow<List<StudentWithGroup>>(emptyList())
    val studentsWithGroup: StateFlow<List<StudentWithGroup>> = _studentsWithGroup


    init {
        // Завантажуємо студентів при ініціалізації ViewModel
        loadStudentsWithGroup()
    }

    private fun loadStudentsWithGroup() {
        viewModelScope.launch {
            _studentsWithGroup.value = database.studentDao().getStudentsWithGroupInfo()
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            database.studentDao().insertStudent(student)
            loadStudentsWithGroup()  // Перезавантажуємо список після додавання нового студента
        }
    }

}
class StudentViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



package com.example.lesson_9

suspend fun prepopulateDatabase(database: AppDatabase) {
    val groups = listOf(
        Group(groupName = "1"),
        Group(groupName = "2"),
        Group(groupName = "3")
    )
    val students = listOf(
        Student(name = "Бажан", surname = "Андренко", age = 20, groupId = 1),
        Student(name = "Батко", surname = "Андресен", age = 21, groupId = 1),
        Student(name = "Біловид", surname = "Андрієвський", age = 19, groupId = 1),
        Student(name = "Білогост", surname = "", age = 18, groupId = 1),
        Student(name = "Біломир", surname = "Андріїв", age = 21, groupId = 1),
        Student(name = "Білослав", surname = "Андріївський", age = 21, groupId = 2),
        Student(name = "Білотур", surname = "Андрійченко", age = 24, groupId = 2),
        Student(name = "Білян", surname = "Андрійчук", age = 25, groupId = 2),
        Student(name = "Благовид", surname = "Андріюк", age = 18, groupId = 2),
        Student(name = "Благовіст", surname = "Андронікашвілі", age = 23, groupId = 3),
        Student(name = "Благодар", surname = "Андроносов", age = 21, groupId = 3),
        Student(name = "Богдан", surname = "Андроносов", age = 25, groupId = 3),
        Student(name = "Богуслав", surname = "Андрисук", age = 22, groupId = 3),
        Student(name = "Божан", surname = "Андрушко", age = 18, groupId = 3)
    )
    database.groupDao().insertGroup(groups)
    database.studentDao().insertStudents(students)  // Використовуємо новий метод
}


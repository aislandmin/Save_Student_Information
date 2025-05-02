package com.xiaomin.guo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Student(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val type: String
)

class StudentsViewModel : ViewModel() {
    private val _students = mutableStateListOf<Student>()
    val students: List<Student> get() = _students

    fun addStudent(student: Student) {
        _students.add(student)
    }
}

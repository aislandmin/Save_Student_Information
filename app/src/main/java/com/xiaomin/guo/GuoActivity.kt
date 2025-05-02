package com.xiaomin.guo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalFocusManager

//Xiaomin Guo 301495284
@Composable
fun GuoActivityContent(studentsViewModel: StudentsViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Regular") } //Regular, Part-Time
    val students = studentsViewModel.students
    val focusManager = LocalFocusManager.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
        ) {
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (name.isEmpty()) Text("Student Name")
                        innerTextField()
                    }
                }
            )
            BasicTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (phoneNumber.isEmpty()) Text("Student Phone Number")
                        innerTextField()
                    }
                }
            )
            BasicTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.padding(8.dp)) {
                        if (email.isEmpty()) Text("Student Email")
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Select Student Type")
            Row {
                RadioButton(selected = (type == "Regular"), onClick = { type = "Regular" })
                Text("Regular")
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(selected = (type == "Part-Time"), onClick = { type = "Part-Time" })
                Text("Part-Time")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Validate input fields and show appropriate snackbar message
                if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Cannot save student information due to missing data")
                    }
                } else {
                    studentsViewModel.addStudent(Student(name, phoneNumber, email, type))
                    scope.launch {
                        snackbarHostState.showSnackbar("Student saved successfully")
                    }
                    // Optionally clear input fields
                    name = ""
                    phoneNumber = ""
                    email = ""
                    type = "Regular"
                    focusManager.clearFocus() // ← This clears the keyboard and cursor
                }
            }) {
                Text("Add Student")
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(students.size) { index ->
                    Text("${students[index].name}: ${students[index].phoneNumber} ${students[index].email} [${students[index].type}]")
                }
            }
        }
    }
}
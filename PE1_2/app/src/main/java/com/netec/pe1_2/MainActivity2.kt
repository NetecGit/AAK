package com.netec.pe1_2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.lazy.items

data class Tarea(val description: String, var completed: Boolean = false)

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListApp()
        }
    }
}

@Composable
fun TaskListApp() {
    var text by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(emptyList<Tarea>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        val newTask = Tarea(text)
                        taskList = taskList.toMutableList() + newTask
                        text = ""
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Agregar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(taskList) { task ->
                TaskItem2(task = task) {
                    taskList = taskList.filter { it != task }
                }
            }
        }
    }
}

@Composable
fun TaskItem2(task: Tarea, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyLarge,
            )

            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Eliminar",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onDeleteClick()
                    }
                    .padding(4.dp),
                tint = Color.Blue
            )
        }
    }
}

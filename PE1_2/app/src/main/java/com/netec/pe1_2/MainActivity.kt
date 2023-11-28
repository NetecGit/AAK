package com.netec.pe1_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netec.pe1_2.ui.theme.PE1_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PE1_2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    TaskList()
                }
            }
        }
    }
}

data class Task(val description: String, val completed: Boolean)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaskList() {

    var text by remember { mutableStateOf("") }

    var itemList by remember {
        mutableStateOf(
            mutableListOf(
                Task("Tarea 1", false),
                Task("Tarea 2", false),
                Task("Tarea 3", false)
            )
        )
    }

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
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 10.sp
                ),
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f),
                placeholder = { Text(text = "Ingresa texto") }
            )

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        val newTask = Task(text, false)
                        // itemList.add(newTask)
                        itemList = (itemList.toMutableList() + newTask).toMutableList()
                        text = ""
                    }
                },
                modifier = Modifier
                    .padding(2.dp)
                    .weight(0.5f)
            ) {
                Text(
                    text = "Add",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(itemList) { item ->
                TaskItem(task = item) {
                    // Esto se llama cuando se hace clic en el Icon para eliminar
                    itemList = itemList.filter { it != item }.toMutableList()
                }
            }
        }

    }
}

@Composable
fun TaskItem(task: Task, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
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
            Spacer(modifier = Modifier.width(40.dp))

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onDeleteClick() // Llama a la función onDeleteClick cuando se hace clic
                    }
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "completada",
                    modifier = Modifier.size(18.dp),
                    tint = Color.Blue
                )
            }
        }
    }
}


fun max() : Int {
    return Int.MAX_VALUE
}

fun min () : Int {
    return Int.MIN_VALUE
}
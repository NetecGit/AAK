package com.netec.pe7_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netec.pe7_1.ui.theme.PE7_1Theme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

import com.netec.pe7_1.parte_uno.Usuario

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PE7_1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

// Lista para almacenar los usuarios
var listaUsuarios = mutableListOf<Usuario>()

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("form") }

    when (currentScreen) {
        "form" -> UserForm(onViewUsers = { currentScreen = "viewUsers" })
        "viewUsers" -> ViewUsersScreen(onBack = { currentScreen = "form" })
    }
}

// Formulario para agregar usuarios
@Composable
fun UserForm(onViewUsers: () -> Unit) {
    val nombre = remember { mutableStateOf("") }
    val edad = remember { mutableStateOf("") }
    val esRobot = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Nombre:")
        BasicTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .semantics { contentDescription = "ValorNombre" }
        )

        Text(text = "Edad:")
        BasicTextField(
            value = edad.value,
            onValueChange = { edad.value = it },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .semantics { contentDescription = "ValorEdad" }
        )

        // Checkbox
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = esRobot.value,
                onCheckedChange = { esRobot.value = it },
                modifier = Modifier.semantics {
                    contentDescription = "ValorRobot"
                }
            )
            Text(text = "No soy un robot")
        }

        // Botón para agregar usuario
        Button(
            onClick = {
                if (nombre.value.isNotBlank() && edad.value.isNotBlank()) {
                    listaUsuarios.add(Usuario(nombre.value, edad.value.toInt(), esRobot.value))
                    nombre.value = ""
                    edad.value = ""
                    esRobot.value = false
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Agregar")
        }

        // Botón para ver usuarios
        Button(
            onClick = onViewUsers,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Ver Usuarios")
        }
    }
}

@Composable
fun ViewUsersScreen(onBack: () -> Unit) {
    // Manejar el botón de regreso del dispositivo
    BackHandler(onBack = onBack)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Usuarios", style = MaterialTheme.typography.bodyMedium)
        listaUsuarios.forEach { usuario ->
            Text("${usuario.nombre} ${usuario.edad} ${if (usuario.esRobot) "No" else "Si"} robot")
        }
    }
}

package com.netec.pe8_1.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.netec.pe8_1.data.model.Contacto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactForm(onSubmit: (Contacto) -> Unit) {

    var name = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var phoneNumber = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Agrega un nuevo contacto", modifier = Modifier.padding(bottom = 8.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nombre") },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Número Telefónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                onSubmit(Contacto(name.value, email.value, phoneNumber.value))
                name.value = ""
                email.value= ""
                phoneNumber.value= ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Contacto")
        }
    }
}

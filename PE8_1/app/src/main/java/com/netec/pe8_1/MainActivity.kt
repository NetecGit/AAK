package com.netec.pe8_1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netec.pe8_1.data.model.Contacto
import com.netec.pe8_1.ui.theme.PE8_1Theme
import com.netec.pe8_1.views.ContactForm

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PE8_1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScreen(this)
                }
            }
        }
    }
}

@Composable
fun AppScreen(contexto: Context) {
    ContactForm(onSubmit = { contacto ->
        Log.d(">>>>", "Contacto guardado: $contacto")
        saveContactToPreferences(contacto, contexto)
    })
}

fun saveContactToPreferences(contact: Contacto, context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "contactos",
        Context.MODE_PRIVATE
    )
    with(sharedPreferences.edit()) {
        putString("name", contact.name)
        putString("email", contact.email)
        putString("phone", contact.phoneNumber)
        apply()
    }
}
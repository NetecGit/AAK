package com.netec.pe2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netec.pe2_1.ui.theme.PE2_1Theme
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PE2_1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MVVMPracticaApp()
                }
            }
        }
    }
}

@Composable
private fun MVVMPracticaApp() {
    val viewModel: MainScreenViewModel = viewModel()
    MainScreen(
        viewModel = viewModel
    )
}

data class MainScreenState(
    val notes: List<Note>,
    val isLoading: Boolean,
    val error: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.toast_error_message)
    val circularLoadingIconDescription = stringResource(id = R.string.circular_loading_icon_description)
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MVVM")
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            var note by remember { mutableStateOf(TextFieldValue("")) }
            TextField(
                value = note,
                onValueChange = {
                    note = it
                },
                placeholder = { Text(text = stringResource(id = R.string.textfield_note_hint)) },
                label = { Text(text = stringResource(id = R.string.note_name)) },
                modifier = Modifier.fillMaxWidth().padding(5.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if (note.text.isNotEmpty()) {
                    viewModel.saveNote(Note(note.text))
                    note = TextFieldValue("")
                } else {
                    Toast.makeText(
                        context, errorMessage, Toast.LENGTH_LONG
                    ).show()
                }

            }) {
                Text(text = stringResource(id = R.string.save))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        vertical = 8.dp,
                        horizontal = 8.dp
                    )
                ) {
                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            onDeleteButtonClick = { viewModel.deleteNote(note) }
                        )
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(
                        Modifier.semantics {
                            circularLoadingIconDescription.also { this.contentDescription = it }
                        }
                    )
                }
                if (state.error != null) {
                    Text(text = state.error)
                }
                if (state.notes.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.empty_list_mesage),
                        modifier = Modifier
                            .align(alignment = Alignment.BottomCenter)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDeleteButtonClick: () -> Unit
) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.padding(8.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = note.name
            )
            IconButton(
                onClick = onDeleteButtonClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.delete),
                )
            }
        }
    }
}

class MainScreenViewModel : ViewModel() {
    private val _state = mutableStateOf(
        MainScreenState(
            notes = listOf(),
            isLoading = true
        )
    )

    val state: State<MainScreenState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, excepetion ->
        excepetion.printStackTrace()
        _state.value = _state.value.copy(
            error = excepetion.message,
            isLoading = false
        )
    }

    private val repository = NoteRepository()

    init {
        getNotes()
    }

    fun getNotes() {
        viewModelScope.launch(errorHandler + Dispatchers.Main) {
            val notes = repository.returnList()
            _state.value = state.value.copy(
                notes = notes,
                isLoading = false
            )
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch(errorHandler + Dispatchers.Main) {
            repository.save(note)
            val updatedNotes = repository.returnList()
            _state.value = _state.value.copy(notes = updatedNotes)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(errorHandler + Dispatchers.Main) {
            repository.delete(note)
            val updatedNotes = repository.returnList()
            _state.value = _state.value.copy(notes = updatedNotes)
        }
    }

}


class NoteRepository {
    private val noteList: MutableList<Note> = arrayListOf()

    fun save(note: Note) {
        noteList.add(note)
    }

    fun delete(note: Note) {
        noteList.remove(note)
    }

    fun returnList() = noteList.toList()
}

data class Note(val name: String)

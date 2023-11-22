package com.netec.pe2_2.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.netec.pe2_2.R
import com.netec.pe2_2.model.Note
import com.netec.pe2_2.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.toast_error_message)
    val circularLoadingIconDescription =
        stringResource(id = R.string.circular_loading_icon_description)
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Practice Hilt")
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            var note by remember {
                mutableStateOf(TextFieldValue(""))
            }
            TextField(
                value = note,
                onValueChange = {
                    note = it
                },
                placeholder = { Text(text = stringResource(id = R.string.textfield_note_hint)) },
                label = { Text(text = stringResource(id = R.string.note_name)) },
                modifier = Modifier.fillMaxWidth(),

                )
            Button(onClick = {
                if (note.text.isNotEmpty()) {
                    viewModel.saveNote(Note(note.text))
                    note = TextFieldValue("")
                } else {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }) {
                Text(text = stringResource(id = R.string.save))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
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
    Card(
        modifier = Modifier
            .padding(8.dp)
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

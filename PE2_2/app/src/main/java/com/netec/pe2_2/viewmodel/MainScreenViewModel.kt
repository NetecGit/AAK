package com.netec.pe2_2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.netec.pe2_2.data.NoteRepository
import com.netec.pe2_2.model.Note
import com.netec.pe2_2.view.MainScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
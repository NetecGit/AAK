package com.netec.pe2_2.view

import com.netec.pe2_2.model.Note

data class MainScreenState(
    val notes: List<Note>,
    val isLoading: Boolean,
    val error: String? = null
)

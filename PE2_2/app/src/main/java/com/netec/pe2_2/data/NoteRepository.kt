package com.netec.pe2_2.data

import com.netec.pe2_2.model.Note

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
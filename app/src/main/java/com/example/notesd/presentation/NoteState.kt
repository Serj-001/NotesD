package com.example.notesd.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesd.data.Note

data class NoteState (

    val notes: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf(""),
    val dateAdded: MutableState<Long>? = null
)
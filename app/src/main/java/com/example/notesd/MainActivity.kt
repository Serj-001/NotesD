package com.example.notesd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesd.data.NoteDatabase
import com.example.notesd.presentation.AddNoteScreen
import com.example.notesd.presentation.NoteViewModel
import com.example.notesd.presentation.NotesScreen
import com.example.notesd.ui.theme.NotesDTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "note.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "NotesScreen") {
                        composable("NotesScreen"){
                            NotesScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }

                        composable("AddNoteScreen"){
                            AddNoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}
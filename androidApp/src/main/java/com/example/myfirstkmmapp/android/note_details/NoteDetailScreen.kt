package com.example.myfirstkmmapp.android.note_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val state by viewModel.state.collectAsState()
    val hasNoteBeenSaved by viewModel.hasNoteBeenSave.collectAsState()


    LaunchedEffect(key1 = hasNoteBeenSaved) {
        if (hasNoteBeenSaved) {
            navController.popBackStack()
        }
    }


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = viewModel::saveNote,
            backgroundColor = Color.Black) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "add", tint = Color.White
            )
        }
    })

  /*  Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveNote,
                backgroundColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note",
                    tint = Color.White
                )
            }
        }
    )*/{ padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color(state.noteColor))
        ) {
            TransparentHintTextField(
                text = state.noteTitle,
                hint = "Enter a title...",
                isHintVisible = state.isNoteTitleHintVisible,
                onValueChange = viewModel::onTitleChange,
                onFocusChange = {
                    viewModel.isTitleFocus(it.isFocused)
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = state.noteContent,
                hint = "Enter some content...",
                isHintVisible = state.isNoteContentHintVisible,
                onValueChange = viewModel::onContentChange,
                onFocusChange = {
                    viewModel.isContentFocus(it.isFocused)
                },
                singleLine = false,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.weight(1f)
            )
        }


    }

}


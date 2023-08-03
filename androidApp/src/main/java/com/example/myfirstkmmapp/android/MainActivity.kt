package com.example.myfirstkmmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfirstkmmapp.android.di.Constant
import com.example.myfirstkmmapp.android.node_list.NoteListScreen
import com.example.myfirstkmmapp.android.note_details.NoteDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "note_list") {
                    composable(route = "note_list") {
                        NoteListScreen(navController = navController)
                    }

                    composable(
                        route = "note_detail/{${Constant.NoteId}}",
                        arguments = listOf(navArgument(name = Constant.NoteId) {
                            type = NavType.LongType
                            defaultValue = -1L
                        })
                    ) { navBackStackEntry ->

                        val noteId = navBackStackEntry.arguments?.getLong(Constant.NoteId) ?: -1
                        NoteDetailScreen(noteId = noteId, navController = navController)


                    }
                }

            }
        }
    }
}


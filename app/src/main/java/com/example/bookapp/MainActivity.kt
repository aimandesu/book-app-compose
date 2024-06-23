package com.example.bookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.bookapp.book.presentation.SetupNavGraph
import com.example.bookapp.ui.theme.BookAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(true) }

            fun toggleTheme(): Boolean {
                isDarkTheme = !isDarkTheme
                return isDarkTheme // Return the new value of isDarkTheme
            }

            BookAppTheme(
                darkTheme = isDarkTheme,
            ) {
                val navController = rememberNavController()
                SetupNavGraph(
                    navHostController = navController,
                    onToggleTheme = { toggleTheme() }
                )
            }

        }
    }
}
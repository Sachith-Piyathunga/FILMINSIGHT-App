package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.components.StandardButton

/**
 * A composable screen that allows users to insert a predefined list of movies into the local database.
 * Also includes a button to navigate back to the main screen.
 *
 * @param addMoviesToDb Function to call when the "Add Movies" button is clicked.
 * @param onBackClick Function to execute when the "Back to Main Screen" button is clicked.
 */
@Composable
fun AddMoviesScreen(
    addMoviesToDb: () -> Unit,
    onBackClick: () -> Unit
) {
    // State to track whether movies have been added
    var isAdded by remember { mutableStateOf(false) }

    // Main column layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show add movies prompt if movies are not yet added
        if (!isAdded) {
            Text("Click the button to add predefined movies to the database")

            Spacer(modifier = Modifier.height(16.dp))

            // Button to trigger movie insertion
            StandardButton(
                text = "Add Movies into Database",
                onClick = {
                    addMoviesToDb()  // Call external logic to add movies
                    isAdded = true   // Update state to show success message
                }
            )
        } else {
            // Success message once movies are added
            Text("Movies have been added to the database successfully!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back to the main screen
        StandardButton(
            text = "Back to Main Screen",
            onClick = onBackClick
        )
    }
}

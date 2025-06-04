package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.*

/**
 * Composable function that allows the user to search for a movie by title
 * and optionally save the result into the database.
 *
 * @param currentMovie The movie object returned from the search (if any).
 * @param isLoading Whether a loading indicator should be displayed.
 * @param error Optional error message to be displayed.
 * @param onSearchMovie Function to trigger a movie search.
 * @param onSaveMovie Function to save the retrieved movie into the database.
 * @param onBackClick Callback to navigate back to the main screen.
 */
@Composable
fun SearchMoviesScreen(
    currentMovie: Movie?,
    isLoading: Boolean,
    error: String?,
    onSearchMovie: (String) -> Unit,
    onSaveMovie: () -> Unit,
    onBackClick: () -> Unit
) {
    // Stores the current user input from the search field
    var searchQuery by remember { mutableStateOf("") }

    // Controls visibility of the "Save Movie" button
    var showSaveButton by remember { mutableStateOf(false) }

    // Root layout container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input field for entering the movie title
        StandardTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = "Enter movie title",
            onSearch = { onSearchMovie(searchQuery) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Row for "Retrieve" and "Save" buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Button to trigger the movie retrieval
            StandardButton(
                text = "Retrieve Movie",
                onClick = {
                    onSearchMovie(searchQuery)
                    showSaveButton = true
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Button to save the current movie into the database
            StandardButton(
                text = "Save Movie into Database",
                onClick = onSaveMovie,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display appropriate UI state
        when {
            isLoading -> LoadingIndicator() // Show loading spinner
            error != null -> ErrorText(message = error) // Show error message
            currentMovie != null -> MovieDetailCard(movie = currentMovie) // Show movie details
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back to the main screen
        StandardButton(
            text = "Back to Main Screen",
            onClick = onBackClick
        )
    }
}

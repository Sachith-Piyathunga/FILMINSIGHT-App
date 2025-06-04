package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.components.StandardButton

/**
 * Home screen of the movie app.
 * Displays navigation buttons for different actions like adding movies,
 * searching movies, searching actors, and searching movies by title.
 *
 * @param onAddMoviesClick Callback for "Add Movies into Database" button.
 * @param onSearchMoviesClick Callback for "Search for Movies" button.
 * @param onSearchActorsClick Callback for "Search for Actors" button.
 * @param onSearchMoviesByTitleClick Callback for "Search Movies by Title" button.
 */
@Composable
fun HomeScreen(
    onAddMoviesClick: () -> Unit,
    onSearchMoviesClick: () -> Unit,
    onSearchActorsClick: () -> Unit,
    onSearchMoviesByTitleClick: () -> Unit
) {
    // Main layout container for the home screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App title displayed at the top
        Text(
            text = "FILMINSIGHT the New Movie App",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Button to add predefined movies to the local database
        StandardButton(
            text = "Add Movies into Database",
            onClick = onAddMoviesClick
        )

        Spacer(modifier = Modifier.height(8.dp)) // Spacing between buttons

        // Button to navigate to the search movies screen
        StandardButton(
            text = "Search for Movies",
            onClick = onSearchMoviesClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to navigate to the search by actors screen
        StandardButton(
            text = "Search for Actors",
            onClick = onSearchActorsClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to navigate to the search by movie title screen
        StandardButton(
            text = "Search Movies by Title",
            onClick = onSearchMoviesByTitleClick
        )
    }
}

package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Movie
import com.example.movieapp.ui.components.*
import com.example.movieapp.viewmodel.MovieViewModel

/**
 * Composable function for searching movies by actor name.
 * Utilizes MovieViewModel to fetch matching movie data.
 *
 * @param viewModel The ViewModel responsible for movie data and search logic.
 * @param onBackClick Callback for navigating back to the main screen.
 */
@Composable
fun SearchActorsScreen(
    viewModel: MovieViewModel,
    onBackClick: () -> Unit
) {
    // State to store the actor name entered by the user
    var searchQuery by remember { mutableStateOf("") }

    // State to store the list of movies returned from actor search
    var actorSearchResults by remember { mutableStateOf(emptyList<Movie>()) }

    // Trigger a search whenever the search query changes and is not empty
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMoviesByActor(searchQuery).collect {
                actorSearchResults = it
            }
        }
    }

    // Layout for the screen content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text input for the actor's name
        StandardTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = "Enter actor name",
            onSearch = { /* Handled by LaunchedEffect */ }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Button to manually initiate the search (although logic is handled in LaunchedEffect)
        StandardButton(
            text = "Search",
            onClick = { /* Handled by LaunchedEffect */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conditional UI rendering based on user input and results
        when {
            searchQuery.isEmpty() -> {
                Text("Enter an actor name to search")
            }
            actorSearchResults.isEmpty() -> {
                Text("No movies found with this actor")
            }
            else -> {
                // Display movie cards for search results
                LazyColumn {
                    items(actorSearchResults) { movie ->
                        MovieDetailCard(movie = movie)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate back to the home screen
        StandardButton(
            text = "Back to Main Screen",
            onClick = onBackClick
        )
    }
}

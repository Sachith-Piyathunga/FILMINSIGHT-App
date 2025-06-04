package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.SearchItem
import com.example.movieapp.ui.components.*

/**
 * Composable function to search movies by title.
 *
 * @param searchResults List of movies returned from the search.
 * @param isLoading Indicates if a search operation is currently in progress.
 * @param error Optional error message to be displayed in case of failure.
 * @param onSearchMovies Lambda to be triggered when a search is initiated.
 * @param onBackClick Callback to return to the main screen.
 */
@Composable
fun SearchMoviesByTitleScreen(
    searchResults: List<SearchItem>,
    isLoading: Boolean,
    error: String?,
    onSearchMovies: (String) -> Unit,
    onBackClick: () -> Unit
) {
    // Holds the current value of the search input
    var searchQuery by remember { mutableStateOf("") }

    // Layout for the screen's content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text field for entering the movie title
        StandardTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = "Enter movie title",
            onSearch = { onSearchMovies(searchQuery) } // Trigger search on keyboard action
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search button to trigger the search function
        StandardButton(
            text = "Search",
            onClick = { onSearchMovies(searchQuery) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display content based on current UI state
        when {
            isLoading -> LoadingIndicator() // Show loading spinner
            error != null -> ErrorText(message = error) // Show error message
            searchResults.isEmpty() -> Text("No movies found. Try again with different search term.") // Empty state
            else -> {
                // Display search results in a scrollable list
                LazyColumn {
                    items(searchResults) { result ->
                        SearchResultItem(searchItem = result)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to return to the home screen
        StandardButton(
            text = "Back to Main Screen",
            onClick = onBackClick
        )
    }
}

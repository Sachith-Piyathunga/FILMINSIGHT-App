package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.viewmodel.SharedViewModel
import com.example.movieapp.ui.screens.*
import com.example.movieapp.ui.theme.MovieAppTheme

// Main activity of the app, sets the Composable UI content
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content using Jetpack Compose
        setContent {
            MovieAppTheme {
                // Surface provides background styling from MaterialTheme
                Surface(color = MaterialTheme.colorScheme.background) {
                    MovieApp() // Launch the main Movie app Composable
                }
            }
        }
    }
}

// Main Composable that sets up the app's navigation and view models
@Composable
fun MovieApp() {
    val navController = rememberNavController()

    // ViewModel for handling movie-related operations (local + remote)
    val movieViewModel: MovieViewModel = viewModel()

    // SharedViewModel to manage global/shared UI state across screens
    val sharedViewModel: SharedViewModel = viewModel()

    // Track current route for screen-aware behaviors (like saving/restoring state)
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route ?: "home"

    // Update the current screen in shared view model when navigation changes
    LaunchedEffect(currentDestination) {
        sharedViewModel.setCurrentScreen(currentDestination)
    }

    // Collect states from MovieViewModel to be passed into the respective screens
    val currentMovie by movieViewModel.currentMovie.collectAsState()
    val searchResults by movieViewModel.searchResults.collectAsState()
    val isLoading by movieViewModel.isLoading.collectAsState()
    val error by movieViewModel.error.collectAsState()

    // Shared UI-related states for persistence or cross-screen updates
    val currentSearchQuery by sharedViewModel.currentSearchQuery.collectAsState()
    val operationSuccessful by sharedViewModel.operationSuccessful.collectAsState()

    // Define navigation graph with routes and corresponding Composables
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // Home screen navigation
        composable("home") {
            HomeScreen(
                onAddMoviesClick = { navController.navigate("addMovies") },
                onSearchMoviesClick = { navController.navigate("searchMovies") },
                onSearchActorsClick = { navController.navigate("searchActors") },
                onSearchMoviesByTitleClick = { navController.navigate("searchMoviesByTitle") }
            )
        }

        // Add movies to the local database
        composable("addMovies") {
            AddMoviesScreen(
                addMoviesToDb = { movieViewModel.addPredefinedMoviesToDB() },
                onBackClick = { navController.navigate("home") }
            )
        }

        // Search for a movie by title using remote API
        composable("searchMovies") {
            SearchMoviesScreen(
                currentMovie = currentMovie,
                isLoading = isLoading,
                error = error,
                onSearchMovie = { title -> movieViewModel.searchMovieByTitle(title) },
                onSaveMovie = { movieViewModel.saveCurrentMovieToDB() },
                onBackClick = { navController.navigate("home") }
            )
        }

        // Search movies by actor name from local database
        composable("searchActors") {
            SearchActorsScreen(
                viewModel = movieViewModel,
                onBackClick = { navController.navigate("home") }
            )
        }

        // Search for multiple movies by title using remote API
        composable("searchMoviesByTitle") {
            SearchMoviesByTitleScreen(
                searchResults = searchResults,
                isLoading = isLoading,
                error = error,
                onSearchMovies = { query -> movieViewModel.searchMoviesByTitleRemote(query) },
                onBackClick = { navController.navigate("home") }
            )
        }
    }
}

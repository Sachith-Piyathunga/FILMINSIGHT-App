package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This ViewModel is used to share state between screens and preserve state during configuration changes
 * like screen rotations.
 */
class SharedViewModel : ViewModel() {

    // For tracking current screen to restore state after rotation
    private val _currentScreen = MutableStateFlow<String>("home")
    val currentScreen: StateFlow<String> = _currentScreen

    // For storing temporary movie data between screens
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    // For tracking search terms to restore after rotation
    private val _currentSearchQuery = MutableStateFlow<String>("")
    val currentSearchQuery: StateFlow<String> = _currentSearchQuery

    // For tracking if a database operation was successful
    private val _operationSuccessful = MutableStateFlow<Boolean>(false)
    val operationSuccessful: StateFlow<Boolean> = _operationSuccessful

    /**
     * Sets the current screen for navigation tracking
     */
    fun setCurrentScreen(screen: String) {
        _currentScreen.value = screen
    }

    /**
     * Stores a movie for use across screens
     */
    fun setSelectedMovie(movie: Movie?) {
        _selectedMovie.value = movie
    }

    /**
     * Stores the current search query
     */
    fun setSearchQuery(query: String) {
        _currentSearchQuery.value = query
    }

    /**
     * Records success status of database operations
     */
    fun setOperationSuccessful(successful: Boolean) {
        _operationSuccessful.value = successful
    }

    /**
     * Clears temporary data
     */
    fun clearTemporaryData() {
        _selectedMovie.value = null
        _currentSearchQuery.value = ""
        _operationSuccessful.value = false
    }
}
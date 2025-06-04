package com.example.movieapp.data.remote

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.SearchItem

/**
 * Repository class to handle remote data operations
 * Uses standard Android APIs instead of third-party libraries
 */
class MovieRemoteRepository {
    private val apiService = MovieApiService()

    /**
     * Get movie by title from the remote API
     */
    suspend fun getMovieByTitle(title: String): Result<Movie> {
        return apiService.getMovieByTitle(title)
    }

    /**
     * Search for movies by title from the remote API
     */
    suspend fun searchMovies(searchTerm: String): Result<List<SearchItem>> {
        return apiService.searchMovies(searchTerm)
    }
}
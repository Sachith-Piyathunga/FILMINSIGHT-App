package com.example.movieapp.data.local

import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that abstracts access to the Movie data source.
 * Acts as a clean API for the rest of the app to interact with the database.
 *
 * @param movieDao The DAO for accessing movie data.
 */
class MovieRepository(private val movieDao: MovieDao) {

    /**
     * Inserts a single movie into the database.
     * If a movie with the same ID exists, it will be replace.
     *
     * @param movie The Movie object to be inserte.
     */
    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    /**
     * Inserts a list movies into the database.
     * Existing entries with matching IDs will be replace.
     *
     * @param movies A list Movie objects to be inserte.
     */
    suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

    /**
     * Searches for movies in the database where the actor's name matches the query.
     *
     * @param actorName The actor name to search for (case insensitive).
     * @return A Flow emitting a list matching Movie objects.
     */
    fun searchMoviesByActor(actorName: String): Flow<List<Movie>> {
        return movieDao.searchMoviesByActor(actorName)
    }

    /**
     * Retrieves all movies stored in the database.
     *
     * @return A Flow emitting the full list Movie objects in the database.
     */
    fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }
}

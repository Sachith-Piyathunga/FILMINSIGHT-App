package com.example.movieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for performing database operations on the Movie entity.
 */
@Dao
interface MovieDao {

    /**
     * Inserts single movie into the database.
     * If the movie already exists, it will be replaced.
     *
     * @param movie The movie to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    /**
     * Inserts a list movies into the database.
     * If any movie already exists, it will be replaced.
     *
     * @param movies The list movies to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    /**
     * Retrieves a movie from the database by it IMDb ID.
     *
     * @param id The IMDb ID of the movie.
     * @return The movie with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM movies WHERE imdbID = :id")
    suspend fun getMovieById(id: String): Movie?

    /**
     * Searches for movies in the database that include a given actor in the 'actors' field.
     * This search is case insensitive and matches partial names.
     *
     * @param actorName The name of the actor to search for.
     * @return A Flow that emits a list of matching movies.
     */
    @Query("SELECT * FROM movies WHERE actors LIKE '%' || :actorName || '%' COLLATE NOCASE")
    fun searchMoviesByActor(actorName: String): Flow<List<Movie>>

    /**
     * Retrieves all movies stored in the database.
     *
     * @return A Flow that emits the full list movies.
     */
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>
}

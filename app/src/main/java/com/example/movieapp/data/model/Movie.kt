package com.example.movieapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.movieapp.data.model.RatingsTypeConverter

/**
 * Entity class representing a Movie record in the Roomdatabase.
 */
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val imdbID: String,         // Unique identifier for the movie (from IMDb)
    val title: String,          // Movie title
    val year: String,           // Year the movie was released
    val rated: String,          // Movie rating
    val released: String,       // Release date
    val runtime: String,        // Duration of the movie
    val genre: String,          // Genre(s)
    val director: String,       // Director(s) of the movie
    val writer: String,         // Writer(s)
    val actors: String,         // Actor(s) in the movie
    val plot: String,           // Movie description
    val language: String,       // Language(s) of the movie
    val country: String,        // Country where the movie was produced
    val awards: String,         // Awards the movie has received

    @TypeConverters(RatingsTypeConverter::class)
    val ratings: List<Rating>?, // List of ratings from various sources

    val metascore: String,      // Metascore rating
    val imdbRating: String,     // IMDb rating
    val imdbVotes: String,      // Number of IMDb votes
    val type: String,
    val totalSeasons: String?,  // Total number of seasons (if it's a series)
    val response: String        // Response status
)

/**
 * Dataclass representing a single rating from a specific source.
 */
data class Rating(
    val source: String,         // Source of the rating
    val value: String           // Rating value
)

/**
 * Dataclass representing the response from movie search APIcall.
 */
data class SearchResponse(
    val search: List<SearchItem>,   // List of search results
    val totalResults: String,       // Total number of results found
    val response: String            // Response status
)

/**
 * Dataclass representing a single search result item from the API.
 */
data class SearchItem(
    val title: String,          // Title of the movie or series
    val year: String,           // Year of release
    val imdbID: String,         // Unique IMDb identifier
    val type: String,
    val poster: String          // URL to the movie poster
)

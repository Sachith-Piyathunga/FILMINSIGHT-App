package com.example.movieapp.data.remote

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.SearchItem
import com.example.movieapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Service class responsible for handling API calls the OMDb API using native HttpURL Connection.
 */
class MovieApiService {

    /**
     * Fetch detailed movie information by its title.
     *
     * @param title The movie title to search for.
     * @return Result object containing the Movie or an error.
     */
    suspend fun getMovieByTitle(title: String): Result<Movie> {
        return try {
            // Encode title to ensure it's URL safe
            val encodedTitle = URLEncoder.encode(title, "UTF-8")
            val urlString = "${Constants.BASE_URL}?t=$encodedTitle&apikey=${Constants.API_KEY}"

            // Make API call and parse JSON response
            val response = makeApiCall(urlString)
            val jsonObject = JSONObject(response)

            if (jsonObject.optString("Response") == "True") {
                Result.success(parseMovieJson(jsonObject))
            } else {
                Result.failure(Exception(jsonObject.optString("Error", "Unknown error")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    /**
     * Search for a list movies by a search term.
     *
     * @param searchTerm The keyword to search movies by.
     * @return Result object containing a list SearchItem or error.
     */
    suspend fun searchMovies(searchTerm: String): Result<List<SearchItem>> {
        return try {
            // Encode search term for URL
            val encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8")
            val urlString = "${Constants.BASE_URL}?s=$encodedSearchTerm&apikey=${Constants.API_KEY}"

            // Make API call and parse response
            val response = makeApiCall(urlString)
            val jsonObject = JSONObject(response)

            if (jsonObject.optString("Response") == "True") {
                val searchArray = jsonObject.getJSONArray("Search")
                val searchResults = mutableListOf<SearchItem>()

                for (i in 0 until searchArray.length()) {
                    val item = searchArray.getJSONObject(i)
                    searchResults.add(parseSearchItemJson(item))
                }

                Result.success(searchResults)
            } else {
                Result.failure(Exception(jsonObject.optString("Error", "No movies found")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    /**
     * Makes an HTTP GET request to the given URL and returns the response as string.
     *
     * @param urlString The complete URL to send the GET request to.
     * @return The raw response body as string.
     */
    private suspend fun makeApiCall(urlString: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            try {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                response.toString()
            } finally {
                connection.disconnect() // Always close the connection
            }
        }
    }

    /**
     * Converts a JSON object from the OMDb API into a Movie data object.
     *
     * @param json The JSON object containing movie data.
     * @return A Movie object.
     */
    private fun parseMovieJson(json: JSONObject): Movie {
        val ratingsArray = json.optJSONArray("Ratings")
        val ratings = mutableListOf<Rating>()

        // Parse each rating from the array
        if (ratingsArray != null) {
            for (i in 0 until ratingsArray.length()) {
                val ratingJson = ratingsArray.getJSONObject(i)
                ratings.add(
                    Rating(
                        source = ratingJson.optString("Source", ""),
                        value = ratingJson.optString("Value", "")
                    )
                )
            }
        }

        // Create and return the Movie object using parsed data
        return Movie(
            imdbID = json.optString("imdbID", ""),
            title = json.optString("Title", ""),
            year = json.optString("Year", ""),
            rated = json.optString("Rated", ""),
            released = json.optString("Released", ""),
            runtime = json.optString("Runtime", ""),
            genre = json.optString("Genre", ""),
            director = json.optString("Director", ""),
            writer = json.optString("Writer", ""),
            actors = json.optString("Actors", ""),
            plot = json.optString("Plot", ""),
            language = json.optString("Language", ""),
            country = json.optString("Country", ""),
            awards = json.optString("Awards", ""),
            ratings = ratings,
            metascore = json.optString("Metascore", ""),
            imdbRating = json.optString("imdbRating", ""),
            imdbVotes = json.optString("imdbVotes", ""),
            type = json.optString("Type", ""),
            totalSeasons = json.optString("totalSeasons", null),
            response = json.optString("Response", "")
        )
    }

    /**
     * Converts a JSON object from the search response into a SearchItem.
     *
     * @param json The JSON object representing a movie in search results.
     * @return A SearchItem object.
     */
    private fun parseSearchItemJson(json: JSONObject): SearchItem {
        return SearchItem(
            title = json.optString("Title", ""),
            year = json.optString("Year", ""),
            imdbID = json.optString("imdbID", ""),
            type = json.optString("Type", ""),
            poster = json.optString("Poster", "")
        )
    }
}

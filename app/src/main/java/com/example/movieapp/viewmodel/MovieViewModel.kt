package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.local.MovieRepository
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.Rating
import com.example.movieapp.data.model.SearchItem
import com.example.movieapp.data.remote.MovieRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel that manages the state and logic for movie-related operations,
 * including local DB storage and remote API access.
 */
class MovieViewModel(application: Application) : AndroidViewModel(application) {

    // Local repository to interact with Room DB
    private val repository: MovieRepository

    // Remote repository to fetch data from OMDb API
    private val remoteRepository: MovieRemoteRepository = MovieRemoteRepository()

    // StateFlow for holding currently selected or fetched movie
    private val _currentMovie = MutableStateFlow<Movie?>(null)
    val currentMovie: StateFlow<Movie?> = _currentMovie

    // StateFlow for holding remote search results
    private val _searchResults = MutableStateFlow<List<SearchItem>>(emptyList())
    val searchResults: StateFlow<List<SearchItem>> = _searchResults

    // StateFlow for tracking loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // StateFlow for tracking error messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Initialize the ViewModel by setting up the local repository
    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
    }

    /**
     * Adds a predefined list of movies to the local Room database.
     */
    fun addPredefinedMoviesToDB() {
        viewModelScope.launch {
            val movies = createPredefinedMovies()
            repository.insertMovies(movies)
        }
    }

    /**
     * Searches for a movie by its title using the remote API.
     * Updates UI states such as loading, currentMovie, and error.
     */
    fun searchMovieByTitle(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = remoteRepository.getMovieByTitle(title)
                result.fold(
                    onSuccess = { movie ->
                        _currentMovie.value = movie
                    },
                    onFailure = { exception ->
                        _error.value = "Movie not found or API error: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Saves the currently selected movie to the local database.
     */
    fun saveCurrentMovieToDB() {
        viewModelScope.launch {
            currentMovie.value?.let {
                repository.insertMovie(it)
            }
        }
    }

    /**
     * Searches for movies that feature the specified actor from the local database.
     *
     * @param actorName Name of the actor to search for.
     * @return A Flow of list of matching movies.
     */
    fun searchMoviesByActor(actorName: String): Flow<List<Movie>> {
        return repository.searchMoviesByActor(actorName)
    }

    /**
     * Searches for movies remotely based on a search term.
     * Updates search results, error, and loading state.
     */
    fun searchMoviesByTitleRemote(searchTerm: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = remoteRepository.searchMovies(searchTerm)
                result.fold(
                    onSuccess = { movies ->
                        _searchResults.value = movies
                    },
                    onFailure = { exception ->
                        _error.value = "No movies found: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Creates and returns a list of hardcoded Movie objects.
     * These are added to the database to simulate initial data.
     */
    private fun createPredefinedMovies(): List<Movie> {
        return listOf(
            Movie(
                imdbID = "tt0111161",
                title = "The Shawshank Redemption",
                year = "1994",
                rated = "R",
                released = "14 Oct 1994",
                runtime = "142 min",
                genre = "Drama",
                director = "Frank Darabont",
                writer = "Stephen King, Frank Darabont",
                actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                language = "English",
                country = "USA",
                awards = "Nominated for 7 Oscars. Another 21 wins & 36 nominations.",
                ratings = listOf(
                    Rating("Internet Movie Database", "9.3/10"),
                    Rating("Rotten Tomatoes", "91%"),
                    Rating("Metacritic", "80/100")
                ),
                metascore = "80",
                imdbRating = "9.3",
                imdbVotes = "2,494,248",
                type = "movie",
                totalSeasons = null,
                response = "True"
            ),
            Movie(
                imdbID = "tt0068646",
                title = "The Godfather",
                year = "1972",
                rated = "R",
                released = "24 Mar 1972",
                runtime = "175 min",
                genre = "Crime, Drama",
                director = "Francis Ford Coppola",
                writer = "Mario Puzo, Francis Ford Coppola",
                actors = "Marlon Brando, Al Pacino, James Caan",
                plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                language = "English, Italian, Latin",
                country = "USA",
                awards = "Won 3 Oscars. Another 26 wins & 30 nominations.",
                ratings = listOf(
                    Rating("Internet Movie Database", "9.2/10"),
                    Rating("Rotten Tomatoes", "98%"),
                    Rating("Metacritic", "100/100")
                ),
                metascore = "100",
                imdbRating = "9.2",
                imdbVotes = "1,728,398",
                type = "movie",
                totalSeasons = null,
                response = "True"
            ),
            Movie(
                imdbID = "tt0468569",
                title = "The Dark Knight",
                year = "2008",
                rated = "PG-13",
                released = "18 Jul 2008",
                runtime = "152 min",
                genre = "Action, Crime, Drama",
                director = "Christopher Nolan",
                writer = "Jonathan Nolan, Christopher Nolan, David S. Goyer",
                actors = "Christian Bale, Heath Ledger, Aaron Eckhart",
                plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                language = "English, Mandarin",
                country = "USA, UK",
                awards = "Won 2 Oscars. Another 157 wins & 163 nominations.",
                ratings = listOf(
                    Rating("Internet Movie Database", "9.0/10"),
                    Rating("Rotten Tomatoes", "94%"),
                    Rating("Metacritic", "84/100")
                ),
                metascore = "84",
                imdbRating = "9.0",
                imdbVotes = "2,450,723",
                type = "movie",
                totalSeasons = null,
                response = "True"
            ),
            Movie(
                imdbID = "tt0137523",
                title = "Fight Club",
                year = "1999",
                rated = "R",
                released = "15 Oct 1999",
                runtime = "139 min",
                genre = "Drama",
                director = "David Fincher",
                writer = "Chuck Palahniuk, Jim Uhls",
                actors = "Brad Pitt, Edward Norton, Meat Loaf",
                plot = "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.",
                language = "English",
                country = "USA, Germany",
                awards = "Nominated for 1 Oscar. Another 11 wins & 37 nominations.",
                ratings = listOf(
                    Rating("Internet Movie Database", "8.8/10"),
                    Rating("Rotten Tomatoes", "79%"),
                    Rating("Metacritic", "66/100")
                ),
                metascore = "66",
                imdbRating = "8.8",
                imdbVotes = "1,895,610",
                type = "movie",
                totalSeasons = null,
                response = "True"
            ),
            Movie(
                imdbID = "tt0133093",
                title = "The Matrix",
                year = "1999",
                rated = "R",
                released = "31 Mar 1999",
                runtime = "136 min",
                genre = "Action, Sci-Fi",
                director = "Lana Wachowski, Lilly Wachowski",
                writer = "Lana Wachowski, Lilly Wachowski",
                actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                plot = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                language = "English",
                country = "USA",
                awards = "Won 4 Oscars. Another 38 wins & 51 nominations.",
                ratings = listOf(
                    Rating("Internet Movie Database", "8.7/10"),
                    Rating("Rotten Tomatoes", "88%"),
                    Rating("Metacritic", "73/100")
                ),
                metascore = "73",
                imdbRating = "8.7",
                imdbVotes = "1,715,254",
                type = "movie",
                totalSeasons = null,
                response = "True"
            )
        )
    }
}

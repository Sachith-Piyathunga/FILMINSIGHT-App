package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.SearchItem

/**
 * A composable that displays detailed information about a Movie inside a Card.
 *
 * @param movie The movie object containing details to display.
 */
@Composable
fun MovieDetailCard(movie: Movie) {
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            MovieDetailField("Title", movie.title)
            MovieDetailField("Year", movie.year)
            MovieDetailField("Rated", movie.rated)
            MovieDetailField("Released", movie.released)
            MovieDetailField("Runtime", movie.runtime)
            MovieDetailField("Genre", movie.genre)
            MovieDetailField("Director", movie.director)
            MovieDetailField("Writer", movie.writer)
            MovieDetailField("Actors", movie.actors)
            MovieDetailField("Plot", movie.plot)
        }
    }
}

/**
 * A reusable composable for displaying a label and its corresponding value.
 * The label is shown in bold for better readability.
 *
 * @param label The field label.
 * @param value The field value.
 */
@Composable
fun MovieDetailField(label: String, value: String) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("$label: ")
            }
            append(value)
        },
        modifier = Modifier.padding(vertical = 2.dp)
    )
}

/**
 * A composable that displays a movie search result item inside clickable card.
 * Typically used in search result lists.
 *
 * @param searchItem The search result containing title, year, and type.
 * @param onClick Action to perform when the card is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(searchItem: SearchItem, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = searchItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Year: ${searchItem.year}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Type: ${searchItem.type}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

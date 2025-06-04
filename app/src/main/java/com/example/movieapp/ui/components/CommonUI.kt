package com.example.movieapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

/**
 * A reusable standard button component that expands to full width and has padding.
 *
 * @param text The label to display inside the button.
 * @param onClick Callback function when the button is pressed.
 * @param modifier Optional modifier for additional styling or layout customization.
 */
@Composable
fun StandardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = text)
    }
}

/**
 * A reusable outlined text field with search keyboard action.
 *
 * @param value Current input value of the text field.
 * @param onValueChange Callback triggered when the text changes.
 * @param label Placeholder/label text shown above the input.
 * @param onSearch Callback triggered when the user presses the search action on the keyboard.
 * @param modifier Optional modifier for layout customization.
 */
@Composable
fun StandardTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onSearch: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

/**
 * Displays a circular loading indicator.
 * Useful for indicating data loading states.
 */
@Composable
fun LoadingIndicator() {
    CircularProgressIndicator()
}

/**
 * Displays an error message in red color.
 *
 * @param message The error text to display.
 */
@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(8.dp),
        color = androidx.compose.ui.graphics.Color.Red
    )
}

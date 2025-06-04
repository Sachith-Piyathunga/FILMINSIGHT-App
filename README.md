# FILMINSIGHT

# 🎬 FilmInsight – Jetpack Compose Movie App

**FilmInsight** is a modern Android movie app built with **Jetpack Compose**. It allows users to search for movies, view detailed information, and save their favorites locally using Room database.

## 🚀 Features

- 🔍 Search movies by title via [OMDb API](https://www.omdbapi.com/)
- 🎭 Search local movies by actor name
- 🧠 View full movie details: title, plot, cast, ratings, etc.
- 💾 Save favorite movies into a local Room database
- ⚙️ Built with MVVM architecture and StateFlow
- 🎨 Material 3 UI with Jetpack Compose
- 🧭 Navigation using Compose's NavHost

## 🛠️ Tech Stack

- **Jetpack Compose** – Modern UI toolkit for Android
- **Room** – Local database storage
- **Kotlin Coroutines & StateFlow** – Asynchronous and reactive programming
- **KSP** – Kotlin Symbol Processing (for Room)
- **Jetpack Navigation** – Screen transitions in Compose
- **Material 3** – Clean, modern UI components
- **OMDb API** – Movie data source

## 📦 Architecture

- Follows **MVVM (Model-View-ViewModel)** pattern
- Uses **Repository pattern** for clean data access
- All screens are built as **Composable functions** inside a single-activity architecture

## 🖥️ Screens

- Home Screen
- Add Movies (inserts predefined data into Room)
- Search Movies (OMDb API)
- Search Actors (Room)
- Search Movies by Title (multiple results from API)
- View Movie Details

## 🧪 Testing

- JUnit for unit testing
- Compose UI testing tools
- Debug preview support

## 📷 Preview

> Add screenshots or demo GIFs here (optional)


---

**Made with 💻 using Kotlin + Jetpack Compose**
"""

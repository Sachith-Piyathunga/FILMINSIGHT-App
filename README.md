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

### Home Screen

![image alt](https://github.com/Sachith-Piyathunga/FILMINSIGHT-App/blob/53ed9a39355e8135d9595d01e4051c1e5c9bc41f/PNG/mainscreen.png)

### Search Movie Screen

![image alt](https://github.com/Sachith-Piyathunga/FILMINSIGHT-App/blob/393a8330b5d70d8e65d64ec7c545c7cbe872d099/PNG/movie.png)

### Search Movies Screen

![image alt](https://github.com/Sachith-Piyathunga/FILMINSIGHT-App/blob/340b2b982449e0a7f3bd14cb27b6d36002a87f8e/PNG/movies.png)

### Search Actors Screen

![image alt](https://github.com/Sachith-Piyathunga/FILMINSIGHT-App/blob/f4c626da73384730750f568a9f3c49b94efe79ee/PNG/actor.png)

### Movies Database

![image alt](https://github.com/Sachith-Piyathunga/FILMINSIGHT-App/blob/ba3ec25adced7e91c29dd183f1dd6f19dc381fe8/PNG/rommDB.png)

---

**Made with 💻 using Kotlin + Jetpack Compose**
"""

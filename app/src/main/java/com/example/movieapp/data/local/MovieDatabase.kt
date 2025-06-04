package com.example.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.RatingsTypeConverter

/**
 * Room database class for storing Movie data.
 * Uses the Movie entity and a type converter for handling complex typpes.
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(RatingsTypeConverter::class)  // Register the type converter for non-primitive types
abstract class MovieDatabase : RoomDatabase() {

    // Abstract method to get the DAO for movie related database operations
    abstract fun movieDao(): MovieDao

    companion object {
        // Singleton instance ensure only one instance of the database is created
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        /**
         * Returns the singleton instance of the database.
         * If it doesn't exist, it will be created.
         *
         * @param context Application context to avoid leaking activities
         * @return The singleton MovieDatabase instance
         */
        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                // Build the database instance if it doesn't already exist
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database" // Name of the database file
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

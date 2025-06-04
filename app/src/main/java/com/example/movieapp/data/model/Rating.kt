package com.example.movieapp.data.model

import androidx.room.TypeConverter
import org.json.JSONArray
import org.json.JSONObject

/**
 * TypeConverter class for Room to handle conversion between a list of [Rating] objects
 * and a JSON string representation. This allows Room to store complex types in adatabase.
 *
 * Room only supports primitive data types by default, so we use this class to convert
 * [List<Rating>] to a [String] when saving to the database and back to [List<Rating>] when reading.
 */
class RatingsTypeConverter {

    /**
     * Converts a list [Rating] objects into JSON string.
     *
     * @param ratings The list [Rating] objects to convert.
     * @return A JSON string representing the list ratings.
     */
    @TypeConverter
    fun fromRatingsList(ratings: List<Rating>?): String {
        if (ratings == null) return "[]" // Return empty JSON array string if list is null

        val jsonArray = JSONArray()

        // Convert each Rating object to JSONObject and add to JSON Array
        ratings.forEach { rating ->
            val jsonObject = JSONObject()
            jsonObject.put("Source", rating.source)
            jsonObject.put("Value", rating.value)
            jsonArray.put(jsonObject)
        }

        return jsonArray.toString() // Convert the entire array to string
    }

    /**
     * Converts a JSON string into a list [Rating] objects.
     *
     * @param ratingsString The JSON string representing a list ratings.
     * @return A list of [Rating] objects or empty list if input is blank or "[]".
     */
    @TypeConverter
    fun toRatingsList(ratingsString: String): List<Rating>? {
        if (ratingsString.isBlank() || ratingsString == "[]") return emptyList()

        val ratings = mutableListOf<Rating>()
        val jsonArray = JSONArray(ratingsString)

        // Parse each JSONObject from the array into a Rating object
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            ratings.add(
                Rating(
                    source = jsonObject.optString("Source", ""),
                    value = jsonObject.optString("Value", "")
                )
            )
        }

        return ratings
    }
}

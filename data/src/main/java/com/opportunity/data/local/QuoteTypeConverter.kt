package com.opportunity.data.local


import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class QuoteTypeConverter {
    @TypeConverter
    fun fromQuoteListToString(listOfQuoteTags: List<String>): String {
        return Json.encodeToString(listOfQuoteTags)
    }

    @TypeConverter
    fun toQuoteListFromString(json: String): List<String> {
        return Json.decodeFromString(json)
    }

}
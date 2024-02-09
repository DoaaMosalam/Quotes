package com.example.quotes.storage.roomdata


import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class QuoteTypeConverter {
    //    @TypeConverter
//    fun fromQuoteType(quotes: Quotes): String {
//        return quotes.content
//    }
    @TypeConverter
    fun fromQuoteListToString(listOfQuoteTags: List<String>): String {
        return Json.encodeToString(listOfQuoteTags)
    }

    @TypeConverter
    fun toQuoteListFromString(json: String): List<String> {
        return Json.decodeFromString(json)
    }

}
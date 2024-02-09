package com.example.quotes.storage.roomdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuotesEntity::class], version = 1, exportSchema = false)
@TypeConverters(QuoteTypeConverter::class)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDatabaseDao(): QuotesDAO

}
package com.example.quotes.storage.roomdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuotesEntity::class], version = 1, exportSchema = false)
@TypeConverters(QuoteTypeConverter::class)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDatabaseDao(): QuotesDAO

    companion object {
        private val DATABASE_NAME = "QUOTES_DATABASE"
        lateinit var instance: QuotesDatabase

        fun getInstance(context: Context): QuotesDatabase {
            return if (::instance.isInitialized)
                instance
            else Room.databaseBuilder(context, QuotesDatabase::class.java, DATABASE_NAME).build()

        }

        private fun builderDatabase(context: Context): QuotesDatabase {
            return Room.databaseBuilder(context, QuotesDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
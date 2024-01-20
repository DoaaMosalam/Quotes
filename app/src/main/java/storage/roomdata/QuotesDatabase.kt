package storage.roomdata

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
        private val  DATABASE_NAME = "QUOTES_DATABASE"
        @Volatile
        private var instance: QuotesDatabase? = null

        fun getInstance(context: Context): QuotesDatabase {
            return instance ?: synchronized(this) {
                instance ?: builderDatabase(context).also { instance = it }
            }
        }

        private fun builderDatabase(context: Context): QuotesDatabase {
            return Room.databaseBuilder(context, QuotesDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
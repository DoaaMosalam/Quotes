package com.example.quotes.storage.roomdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface QuotesDAO {
    // get all quotes from database
    @Query("SELECT * FROM QUOTES_TABLE ORDER BY id DESC")
    fun getAllQuotesFromData(): Flow<MutableList<QuotesEntity>>

    //insert quotes into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuoteToDatabase(quotes: QuotesEntity)

    //search quotes from database by author and content
    @Query("SELECT * FROM QUOTES_TABLE WHERE author LIKE :searchQuery OR content LIKE :searchQuery")
    fun searchQuotes(searchQuery: String): Flow<List<QuotesEntity>>

    // Delete a specific quote from the database by ID
    @Query("DELETE FROM QUOTES_TABLE WHERE id = :quoteId")
    suspend fun deleteQuoteById(quoteId: Long)
}

package com.example.quotes.repository

import com.example.quotes.storage.roomdata.QuotesDAO
import com.example.quotes.storage.roomdata.QuotesEntity
import kotlinx.coroutines.flow.Flow


class FavoriteRepository(
    private val quotesDAO: QuotesDAO
) {
    // Function to get all quotes from the database
    fun getAllQuotesFromDatabase(): Flow<MutableList<QuotesEntity>> {
        return quotesDAO.getAllQuotesFromData()
    }

    // Function to search quotes from the database by author and content
    fun searchQuotesIntoDatabase(searchQuery: String): Flow<List<QuotesEntity>> {
        return quotesDAO.searchQuotes(searchQuery)
    }

    // Delete a quote by its ID
    suspend fun deleteQuoteById(quoteId: Long) {
        quotesDAO.deleteQuoteById(quoteId)
    }
}
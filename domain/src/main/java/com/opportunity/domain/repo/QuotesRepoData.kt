package com.opportunity.domain.repo

import com.opportunity.domain.model.Quotes
import kotlinx.coroutines.flow.Flow

interface QuotesRepoData {

    fun getAllQuotesFromData(): Flow<List<Quotes>>
    suspend fun insertQuoteToData(quote: Quotes)
    fun searchQuotes(query: String): Flow<List<Quotes>>
    suspend fun deleteQuoteById(quoteId: String )

}
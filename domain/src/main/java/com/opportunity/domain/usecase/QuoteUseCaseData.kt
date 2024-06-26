package com.opportunity.domain.usecase

import com.opportunity.domain.model.Quotes
import com.opportunity.domain.repo.QuotesRepoData


class QuoteUseCaseData(private val quotesRepoData: QuotesRepoData) {
     fun getAllQuotesFromData() = quotesRepoData.getAllQuotesFromData()

    suspend fun insertQuoteToData(quotes: Quotes) = quotesRepoData.insertQuoteToData(quotes)

     fun searchQuotes(query:String) = quotesRepoData.searchQuotes(query)
    suspend fun deleteQuoteById(quoteId: Long) = quotesRepoData.deleteQuoteById(quoteId)


}
package com.opportunity.data.repo

import com.opportunity.data.local.QuotesDAO
import com.opportunity.data.local.QuotesEntity
import com.opportunity.domain.model.Quotes
import com.opportunity.domain.repo.QuotesRepoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuotesRepoImplData(private val quotesDAO: QuotesDAO) : QuotesRepoData {
    override fun getAllQuotesFromData(): Flow<List<Quotes>> {
        return quotesDAO.getAllQuotesFromData().map { entities ->
            entities.map { it.toDomain() }
        }
    }


    override suspend fun insertQuoteToData(quote: Quotes) = quotesDAO.insertQuoteToDatabase(quote.toEntity())
    override fun searchQuotes(query: String): Flow<List<Quotes>> {
        return quotesDAO.searchQuotes(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }


        override suspend fun deleteQuoteById(quoteId: String) = quotesDAO.deleteQuoteById(quoteId)
    private fun QuotesEntity.toDomain() = Quotes(
        _id, author, content, tags, authorSlug, length, dateAdded, dateModified
    )

    private fun Quotes.toEntity() = QuotesEntity(
        id = null, // id will be auto-generated
        _id, author, content, tags, authorSlug, length, dateAdded, dateModified
    )

}
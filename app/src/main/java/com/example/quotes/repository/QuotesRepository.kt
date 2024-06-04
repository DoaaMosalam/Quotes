package com.example.quotes.repository

import com.example.quotes.local.QuotesDAO
import com.example.quotes.local.QuotesEntity
import com.example.quotes.util.RequestStatus
import com.opportunity.data.remote.ApiQuotes
import com.opportunity.domain.model.QuotesResponse


import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class QuotesRepository @Inject constructor(
    private val quotesDAO: QuotesDAO
) {
    //insert quotes into database
    suspend fun insertQuoteToDatabase(quotes: QuotesEntity) =
        quotesDAO.insertQuoteToDatabase(quotes)
}
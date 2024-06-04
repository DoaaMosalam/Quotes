package com.opportunity.data.repo

import com.opportunity.data.remote.ApiQuotes
import com.opportunity.domain.model.QuotesResponse
import com.opportunity.domain.repo.QuotesRepo

class QuotesRepoImp(private val apiQuotes: ApiQuotes):QuotesRepo {
    override suspend fun getAllQuotes(): QuotesResponse =
        apiQuotes.getAllQuotes()

}
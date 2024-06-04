package com.opportunity.domain.repo

import com.opportunity.domain.model.QuotesResponse

interface QuotesRepo {
    suspend fun getAllQuotes():QuotesResponse
}
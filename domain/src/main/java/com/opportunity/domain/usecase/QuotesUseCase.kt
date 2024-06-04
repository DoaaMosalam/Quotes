package com.opportunity.domain.usecase

import com.opportunity.domain.repo.QuotesRepo

class QuotesUseCase(private val quotesRepo: QuotesRepo) {
    suspend fun getAllQuotes()= quotesRepo.getAllQuotes()
}
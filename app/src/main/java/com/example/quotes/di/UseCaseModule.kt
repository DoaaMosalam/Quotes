package com.example.quotes.di

import com.opportunity.domain.repo.QuotesRepo
import com.opportunity.domain.usecase.QuotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun ProvideAuotesUseCase(quotesRepo: QuotesRepo):QuotesUseCase{
        return QuotesUseCase(quotesRepo)
    }
}
package com.example.quotes.di

import com.opportunity.data.remote.ApiQuotes
import com.opportunity.data.repo.QuotesRepoImp
import com.opportunity.domain.repo.QuotesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideQuotesRepo(apiQuotes: ApiQuotes):QuotesRepo{
        return QuotesRepoImp(apiQuotes)
    }

}
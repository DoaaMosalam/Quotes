package com.example.quotes.di

import android.app.Application
import androidx.room.Room
import com.opportunity.data.local.QuotesDAO
import com.opportunity.data.local.QuotesDatabase
import com.opportunity.data.repository.FavoriteRepository
import com.opportunity.data.repository.QuotesRepository
import com.opportunity.data.remote.ApiQuotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuotesModule {
    private val DATABASE_NAME = "QUOTES_DATABASE"
    @Singleton
    @Provides
    fun provideRoomDatabaseBuilder(application: Application): QuotesDatabase {
        return Room.databaseBuilder(
            application,
            QuotesDatabase::class.java, DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideQuotesDAO(quotesDatabase: QuotesDatabase): QuotesDAO {
        return quotesDatabase.quotesDatabaseDao()
    }

    @Singleton
    @Provides
    fun provideQuotesRepository(apiService: ApiQuotes, quotesDAO: QuotesDAO): QuotesRepository {
        return QuotesRepository(apiService, quotesDAO)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(quotesDAO: QuotesDAO): FavoriteRepository {
        return FavoriteRepository(quotesDAO)
    }
}
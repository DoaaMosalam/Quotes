package com.example.quotes.di

import android.app.Application
import androidx.room.Room
import com.example.quotes.local.QuotesDAO
import com.example.quotes.local.QuotesDatabase
import com.example.quotes.repository.FavoriteRepository
import com.example.quotes.repository.QuotesRepository
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
    fun provideQuotesRepository( quotesDAO: QuotesDAO): QuotesRepository {
        return QuotesRepository( quotesDAO)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(quotesDAO: QuotesDAO): FavoriteRepository {
        return FavoriteRepository(quotesDAO)
    }
}
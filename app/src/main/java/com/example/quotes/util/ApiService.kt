package com.example.quotes.util

import android.app.Application
import androidx.room.Room
import com.example.quotes.repository.FavoriteRepository
import com.example.quotes.repository.QuotesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.quotes.storage.roomdata.QuotesDAO
import com.example.quotes.storage.roomdata.QuotesDatabase
import com.example.quotes.viewmodel.FavoriteViewModelFactory
import com.example.quotes.viewmodel.QuotesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiService {
    private val DATABASE_NAME = "QUOTES_DATABASE"
    @Singleton
    @Provides
    fun getService(): ApiQuotes {
        // API response interceptor
        val builder = Retrofit.Builder()
            .baseUrl(Credential.BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        return retrofit.create(ApiQuotes::class.java)
    }
    @Singleton
    @Provides
     fun okHttpClient():OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        // Client
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(16, TimeUnit.SECONDS)
            .writeTimeout(16, TimeUnit.SECONDS)
            .readTimeout(16, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)
        return client.build()

    }
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
    fun provideFavoriteRepository( quotesDAO: QuotesDAO): FavoriteRepository {
        return FavoriteRepository( quotesDAO)
    }
}
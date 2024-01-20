package com.example.quotes.util

import android.app.Application
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.quotes.storage.roomdata.QuotesDAO
import com.example.quotes.storage.roomdata.QuotesDatabase
import java.util.concurrent.TimeUnit


object ApiService {
    fun getService(): ApiQuotes {
        // API response interceptor

        val builder = Retrofit.Builder()
            .baseUrl(Credential.BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        return retrofit.create(ApiQuotes::class.java)
    }
    private fun okHttpClient():OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        // Client
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)
        return client.build()

    }
}
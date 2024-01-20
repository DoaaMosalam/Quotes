package com.example.quotes.util

import com.example.quotes.pojo.QuotesResponse
import com.example.quotes.pojo.Quotes
import retrofit2.Response
import retrofit2.http.GET



interface ApiQuotes {
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>
}
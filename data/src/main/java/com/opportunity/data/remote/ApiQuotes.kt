package com.opportunity.data.remote

import com.opportunity.domain.model.QuotesResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiQuotes {
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>
}
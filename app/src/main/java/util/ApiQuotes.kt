package util

import pojo.QuotesResponse
import pojo.Quotes
import retrofit2.Response
import retrofit2.http.GET



interface ApiQuotes {
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>
}
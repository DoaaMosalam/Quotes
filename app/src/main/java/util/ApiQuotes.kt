package util

import pojo.Quotes
import pojo.QuotesResponse
import retrofit2.Response
import retrofit2.http.GET



interface ApiQuotes {
    @GET("quotes")
   suspend fun getQuotes(): Response<QuotesResponse>
}
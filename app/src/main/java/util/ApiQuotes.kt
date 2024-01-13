package util

import pojo.QuotesResponse
import pojo.Quotes
import retrofit2.Response
import retrofit2.http.GET



interface ApiQuotes {
    @GET("quotes/random")
   suspend fun getQuotes(): Response<List<Quotes>>

//   @GET("/search/quotes")
//   suspend fun getQuotesByAuthor(): Response<List<Quotes>>
}
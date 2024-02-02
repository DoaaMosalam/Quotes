package com.example.quotes.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.quotes.pojo.QuotesResponse
import com.example.quotes.storage.roomdata.QuotesDAO
import com.example.quotes.storage.roomdata.QuotesDatabase
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.ApiQuotes
import com.example.quotes.util.RequestStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response


class QuotesRepository(
    private val apiQuotes: ApiQuotes,
//    quotesDatabase: QuotesDatabase
    private val quotesDAO: QuotesDAO
) {
//    private val quotesDAO = quotesDatabase.quotesDatabaseDao()

    suspend fun getQuotesFromServiceIntoDatabase() = flow {
        emit(RequestStatus.Waiting)
        try {
            val response: Response<QuotesResponse> = apiQuotes.getQuotes()
            if (response.isSuccessful) {
                emit(RequestStatus.Success(response.body()!!))
//                val responseDetails = response.body()
//                responseDetails?.let {
//                    for (quote in responseDetails.results) {
//                        val entity = QuotesEntity(
//                            _id = quote._id,
//                            author = quote.author,
//                            content = quote.content,
//                            tags = quote.tags,
//                            authorSlug = quote.authorSlug,
//                            length = quote.length,
//                            dateAdded = quote.dateAdded,
//                            dateModified = quote.dateModified
//                        )
//                        quotesDAO.insertQuoteToDatabase(listOf(entity))
//                    }
//                }
            } else {
                emit(
                    RequestStatus.Error(
                        response.errorBody()?.byteStream()?.reader()?.readText()
                            ?: "Unknown error"
                    )
                )
            }
        } catch (e: Exception) {
            emit(RequestStatus.Error(e.message ?: "An error occurred"))
        }
    }



    //insert quotes into database
    suspend fun insertQuoteToDatabase(quotes: QuotesEntity) = quotesDAO.insertQuoteToDatabase(quotes)

}
package com.example.quotes.data.repository

import com.example.quotes.domain.model.QuotesResponse
import com.example.quotes.data.local.QuotesDAO
import com.example.quotes.data.local.QuotesEntity
import com.example.quotes.domain.util.ApiQuotes
import com.example.quotes.domain.util.RequestStatus
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


class QuotesRepository @Inject constructor(
    private val apiQuotes: ApiQuotes,
    private val quotesDAO: QuotesDAO
) {
    suspend fun getQuotesFromServiceIntoDatabase() = flow {
        emit(RequestStatus.Waiting)
        try {
            val response: Response<QuotesResponse> = apiQuotes.getQuotes()
            if (response.isSuccessful) {
                emit(RequestStatus.Success(response.body()!!))
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

    //=============================================================================================
    //insert quotes into database
    suspend fun insertQuoteToDatabase(quotes: QuotesEntity) =
        quotesDAO.insertQuoteToDatabase(quotes)
}
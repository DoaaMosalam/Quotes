package repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import pojo.Quotes
import pojo.QuotesResponse
import retrofit2.Response

import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiQuotes
import util.RequestStatus


class QuotesRepository (
    private val apiQuotes: ApiQuotes,
   private val  quotesDatabase: QuotesDatabase
) {
    private val quotesDAO = quotesDatabase.quotesDatabaseDao()

    //create function to get data from api and insert it to database
    suspend fun fetchAndInsertQuotes() = flow {
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
}
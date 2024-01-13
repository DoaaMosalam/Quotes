package repository

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import pojo.QuotesResponse
import retrofit2.Response

import storage.roomdata.QuotesDAO
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiQuotes
import util.RequestStatus




class QuotesRepository (
    private val apiQuotes: ApiQuotes,
    private val quotesDatabase:QuotesDatabase
) {
    suspend fun getQuotes() = flow {
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

//    suspend fun getQuoteFromServerToDatabase()= flow {
//        emit(RequestStatus.Waiting)
//        try{
//            val response = apiQuotes.getQuotes()
//            if(response.isSuccessful){
//                emit(RequestStatus.Success(response.body()!!))
////                val responseDetails = response.body()
////                for (quote in responseDetails!!.results){
////                    val entity = QuotesEntity(
////                        id = quote.id,
////                        quoteType = "REGULAR",
////                        author = quote.author,
////                        content = quote.content,
////                        tags = quote.tags,
////                        authorSlug = quote.authorSlug,
////                        length = quote.length,
////                        dateAdded = quote.dateAdded,
////                        dateModified = quote.dateModified
////                    )
////                    quotesDAO.insertQuoteToData(entity)
////                }
//            }else{
//                emit(RequestStatus.Error(response.errorBody()?.byteStream()?.reader()?.readText()?: "Unknown error"))
//
//            }
//
//        }catch (e:Exception){
//            emit(RequestStatus.Error(e.message ?: "An error occurred"))
//            Log.i("error", "getQuoteFromServerToDatabase: $e")
//
//        }
//    }

    // create funcation to get data from database
//    suspend fun getQuotesFromDatabase(): LiveData<List<QuotesEntity>> {
//        return withContext(Dispatchers.IO) {
//            return@withContext quotesDAO.getAllQuotesFromData()
//        }
//    }
//
//    //create function update quote type
//    suspend fun updateQuoteType(key: String, quoteT: String) {
//        withContext(Dispatchers.IO) {
//            quotesDAO.updateQuoteType(key, quoteT)
//        }
//    }

}
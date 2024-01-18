package repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pojo.Quotes
import storage.roomdata.QuotesDAO
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiQuotes


class QuotesRepository(
    private val apiQuotes: ApiQuotes,
    private val quotesDatabase: QuotesDatabase

) {
    private val quotesDAO = quotesDatabase.quotesDatabaseDao()

 //Function to fetch quotes from the API and insert them into the Room database
 suspend fun fetchQuotes() {
     try {
         val response = apiQuotes.getQuotes()
         if (response.isSuccessful) {
             val quotesResponse = response.body()
             quotesResponse?.let { saveQuotesToDatabase(it.results) }
         } else {
             Log.e("TAG", "getQuoteFromServerToDatabase: ${response.body()}")
         }
     } catch (e: Exception) {
         Log.e("error", "getQuoteFromServerToDatabase: $e")
     }
 }

    private suspend fun saveQuotesToDatabase(quotes: List<Quotes>) {
        val quotesEntities = quotes.map {
            QuotesEntity(
                0, // Auto-generated ID
                "REGULAR", // You may set a default quoteType
                it.author,
                it.content,
                it.tags,
                it.authorSlug,
                it.length,
                it.dateAdded,
                it.dateModified
            )
        }
        quotesDAO.insertQuoteToDatabase(quotesEntities)
    }

    fun getAllQuotesFromDatabase(): LiveData<List<QuotesEntity>> {
        return quotesDAO.getAllQuotesFromData()
    }

    fun searchQuotesFromDatabase(search: String): LiveData<List<QuotesEntity>> {
        return quotesDAO.searchQuotes("%$search%")
    }

    suspend fun deleteAllQuotesFromDatabase() {
        quotesDAO.deleteAllQuotes()
    }

    fun updateQuoteTypeInDatabase(key: Long, quoteType: String) {
        quotesDAO.updateQuoteType(key.toString(), quoteType)
    }
//================================================================================================
//    suspend fun fetchAndInsertQuotes() = flow {
//        emit(RequestStatus.Waiting)
//        try {
//            val response: Response<QuotesResponse> = apiQuotes.getQuotes()
//
//            if (response.isSuccessful) {
//                emit(RequestStatus.Success(response.body()!!))
//            } else {
//                emit(
//                    RequestStatus.Error(
//                        response.errorBody()?.byteStream()?.reader()?.readText()
//                            ?: "Unknown error"
//                    )
//                )
//            }
//        } catch (e: Exception) {
//            emit(RequestStatus.Error(e.message ?: "An error occurred"))
//        }
//    }
}
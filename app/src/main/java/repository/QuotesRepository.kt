package repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiQuotes


class QuotesRepository (
    private val apiQuotes: ApiQuotes,
   private val quotesDatabase: QuotesDatabase
) {

    val quotesDAO = quotesDatabase.quotesDatabaseDao()
 //Function to fetch quotes from the API and insert them into the Room database
    suspend fun fetchQuotesAndInsertIntoDatabase():Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiQuotes.getQuotes()
                if (response.isSuccessful){
                    val result = response.body()?.results
                    result?.let {
                        val quotesEntity =it.map { quotes ->
                            QuotesEntity(
                               id = quotes.id.toLong(),
                                quoteType = "REGULAR",
                                author = quotes.author,
                                content = quotes.content,
                                tags = quotes.tags,
                                authorSlug =   quotes.authorSlug,
                                length = quotes.length,
                               dateAdded = quotes.dateAdded,
                                dateModified = quotes.dateModified
                            )
                        }
                        quotesDAO.insertQuoteToDatabase(quotesEntity)
                        return@withContext true
                    }
                }
                return@withContext false
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

    // Function to get all quotes from the Room database
    fun getAllQuotesFromDatabase(): List<QuotesEntity> {
        return quotesDAO.getAllQuotesFromData()
    }

    // Function to search quotes from the Room database by author and content
    fun searchQuotes(query: String): LiveData<List<QuotesEntity>> {
        return quotesDAO.searchQuotes("%$query%")
    }

    // Function to delete all quotes from the Room database
    suspend fun deleteAllQuotes() {
        withContext(Dispatchers.IO) {
            quotesDAO.deleteAllQuotes()
        }
    }

    // Function to update the quote type in the Room database
    suspend fun updateQuoteType(id: Long, quoteType: String) {
        withContext(Dispatchers.IO) {
            quotesDAO.updateQuoteType(id.toString(), quoteType)
        }
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
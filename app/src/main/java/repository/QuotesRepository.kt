package repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pojo.QuotesResponse
import pojo.Quotes
import storage.roomdata.QuotesDAO
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiQuotes

class QuotesRepository(
    private val apiQuotes: ApiQuotes) {
    val _showProgress = MutableLiveData(true)
    suspend fun getQuotes(): List<Quotes> {
        val response = apiQuotes.getQuotes()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            // Handle error
            emptyList()
        }
    }

//    suspend fun getQuotesFromServerToData() {
//        try {
//            val response = apiQuotes.getQuotes()
//            if (response.isSuccessful){
//                val responseDetails = response.body()
//                for (quote in responseDetails!!){
//                    val entity = QuotesEntity(
//                        id = quote._id,
//                        quoteType = "REGULAR",
//                        author = quote.author,
//                        content = quote.content,
//                        tags = quote.tags,
//                        authorSlug = quote.authorSlug,
//                        length = quote.length,
//                        dateAdded = quote.dateAdded,
//                        dateModified = quote.dateModified
//                    )
//                    quotesDao.insertQuote(entity)
//                }
//            }else {
//                Log.i("TAG", "getQuoteFromServerToDatabase: ${response.body()}")
//                _showProgress.postValue(false)
//            }
//        }catch (e:Exception){
//            Log.i("error", "getQuoteFromServerToDatabase: $e")
//        }
//    }
}
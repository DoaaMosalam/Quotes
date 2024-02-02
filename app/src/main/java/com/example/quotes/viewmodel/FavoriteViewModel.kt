package com.example.quotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.repository.FavoriteRepository
import com.example.quotes.repository.QuotesRepository
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.RequestStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel
    (
            private val repository: FavoriteRepository
) : ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }

    private val _quotesList = MutableLiveData<RequestStatus<List<QuotesEntity>>>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotesList: LiveData<RequestStatus<List<QuotesEntity>>> get() = _quotesList

    fun getAllQuotesFromDatabase(){
        viewModelScope.launch {
            repository.getAllQuotesFromDatabase().collect{
                _isLoad.postValue(true)
                _quotesList.postValue(RequestStatus.Success(it))
            }
        }
    }
    fun deleteSpecialQuoteByID(quoteId: Long) {
        viewModelScope.launch {
            repository.deleteQuoteById(quoteId)
        }
    }

    //fun searchQuotes() search quotes from database by author and content
    fun searchQuotes(searchQuery: String): Flow<List<QuotesEntity>> {
        return repository.searchQuotesIntoDatabase(searchQuery)
    }
}
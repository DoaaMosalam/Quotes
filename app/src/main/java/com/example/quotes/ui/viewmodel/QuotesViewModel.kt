package com.example.quotes.ui.viewmodel

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repository.QuotesRepository
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiService

class QuotesViewModel(
    private val repository: QuotesRepository
) : ViewModel() {

    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }
    private val _quotes = MutableLiveData<List<QuotesEntity>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<QuotesEntity>> get() = _quotes
    val error: LiveData<String> get() = _error

    // Function to fetch quotes from the API and save them in the Room database
    fun fetchQuotes() {
        viewModelScope.launch {
            _isLoad.value=true
            try {
                repository.fetchQuotes()
                _isLoad.value=false
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }


    // Function to get all quotes from the Room database
    fun getAllQuotes():LiveData<List<QuotesEntity>> {
        return repository.getAllQuotesFromDatabase()
    }


    // Function to search quotes in the Room database
    fun searchQuotes(search: String): LiveData<List<QuotesEntity>> {
        return repository.searchQuotesFromDatabase(search)
    }

    // Function to delete all quotes from the Room database
    fun deleteAllQuotes() = viewModelScope.launch {
        repository.deleteAllQuotesFromDatabase()
    }

    // Function to update quote type in the Room database
    fun updateQuoteType(key: Long, quoteType: String) {
        repository.updateQuoteTypeInDatabase(key, quoteType)
    }

//==================================================================================================

//    fun getQuotesAndInsert() {
//        viewModelScope.launch {
//            quotesRepository.fetchAndInsertQuotes().collect() { requestStatus ->
//                when (requestStatus) {
//                    is RequestStatus.Success -> {
//                        _quotes.postValue(requestStatus.data.results)
//                        _isLoad.postValue(false)
//                    }
//                    is RequestStatus.Error -> {
//                        _error.postValue(requestStatus.message)
//                        _isLoad.postValue(false)
//                    }
//                    is RequestStatus.Waiting -> {
//                        _isLoad.postValue(true)
//                    }
//                }
//            }
//        }
//    }


}





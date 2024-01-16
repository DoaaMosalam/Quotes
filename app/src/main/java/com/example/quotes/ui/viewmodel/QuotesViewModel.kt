package com.example.quotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pojo.Quotes
import repository.QuotesRepository
import storage.roomdata.QuotesEntity
import util.RequestStatus

class QuotesViewModel(
    private val quotesRepository: QuotesRepository
):ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }

    private val _quotes = MutableLiveData<List<Quotes>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<Quotes>> get() = _quotes
    val error: LiveData<String> get() = _error

    // Function to fetch quotes from API and insert them into Room Database
    fun getQuotesAndInsert() {
        viewModelScope.launch {
            quotesRepository.fetchAndInsertQuotes().collect() { requestStatus ->
                when (requestStatus) {
                    is RequestStatus.Success -> {
                        _quotes.postValue(requestStatus.data.results)
                        _isLoad.postValue(false)
                    }
                    is RequestStatus.Error -> {
                        _error.postValue(requestStatus.message)
                        _isLoad.postValue(false)
                    }
                    is RequestStatus.Waiting -> {
                        _isLoad.postValue(true)
                    }
                }
            }
        }
    }


}





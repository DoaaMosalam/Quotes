package com.example.quotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.quotes.pojo.Quotes
import com.example.quotes.repository.QuotesRepository
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.RequestStatus

class QuotesViewModel(
    private val repository: QuotesRepository
) : ViewModel() {

    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }
    private val _quotes = MutableLiveData<List<Quotes>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<Quotes>> get() = _quotes
    val error: LiveData<String> get() = _error

    fun getAllQuotesFromService() {
        viewModelScope.launch {
            repository.getQuotesFromService().collect() { requestStatus ->
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
    //=============================================================================================
    fun getQuote() {
        viewModelScope.launch {
            repository.insertQuotesToData()
        }
    }

    fun getQuoteFromDatabase(): LiveData<List<QuotesEntity>> {
        return _isLoad.switchMap {
            if (!it) {
                repository.getQuoteFromDatabase()
            } else {
                null
            }
        }
    }
}





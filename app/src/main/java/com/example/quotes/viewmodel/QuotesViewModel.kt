package com.example.quotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotes.pojo.Quotes
import com.example.quotes.repository.QuotesRepository
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.RequestStatus
import kotlinx.coroutines.launch

class QuotesViewModel(
    private val repository: QuotesRepository
) : ViewModel() {

    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }
    private val _quotes = MutableLiveData<List<Quotes>>()
    private val _error = MutableLiveData<String>()

    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<Quotes>> get() = _quotes
    val error: LiveData<String> get() = _error

//this method get all quotes from API then save them into room database.
    fun getAllQuotesFromServiceIntoDatabase() {
        viewModelScope.launch {
            repository.getQuotesFromServiceIntoDatabase().collect { requestStatus ->
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
    //  this method insert quotes to database
    fun insertQuotesToDatabase(quotes: QuotesEntity) {
        viewModelScope.launch {
            repository.insertQuoteToDatabase(quotes)
        }
    }
}






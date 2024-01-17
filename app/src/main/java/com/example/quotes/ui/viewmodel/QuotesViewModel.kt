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

    private val _quotes = MutableLiveData<List<QuotesEntity>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<QuotesEntity>> get() = _quotes
    val error: LiveData<String> get() = _error

    // Function to fetch quotes from API and insert them into Room Database
    fun fetchAndInsertQuotes() {
        viewModelScope.launch {
            try {
                _isLoad.value = true
                 quotesRepository.fetchQuotesAndInsertIntoDatabase()
//                if (success){
//                    _error.value=""
//                    getAllQuotesFromData()
//                }else{
//                    _error.value = "Failed to fetch and insert quotes."
//                }
                    _error.value = "Failed to fetch and insert quotes."
                _isLoad.value = false
            } catch (e: Exception) {
                _isLoad.value = false
                _error.value = "An error occurred."
            }
        }
    }
     fun getAllQuotesFromData(): List<QuotesEntity>? {
        return try {
            _isLoad.value = true
            val quotes = quotesRepository.getAllQuotesFromDatabase()
            _isLoad.value = false
            quotes
        } catch (e: Exception) {
            _isLoad.value = false
            _error.value = "Error getting quotes from data"
           null
        }
    }

    // Function to load quotes from Room Database
//    private fun loadQuotesFromDatabase() {
//        viewModelScope.launch {
//            try {
//                val quotes = quotesRepository.getAllQuotesFromDatabase()
//                _quotes.value = quotes
//            } catch (e: Exception) {
//                _error.value = "Failed to load quotes from database."
//            }
//        }
//    }





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





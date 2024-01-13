package com.example.quotes.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pojo.Quotes
import pojo.QuotesResponse
import repository.QuotesRepository
import storage.roomdata.QuotesDAO

class QuotesViewModel(
    private val quotesRepository: QuotesRepository
):ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }

    private val _quotes = MutableLiveData<List<Quotes>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<Quotes>> get() = _quotes
    val error: LiveData<String> get() = _error

    fun loadQuotes() {
        viewModelScope.launch {
            try {
                val quotesList = quotesRepository.getQuotes()
                _quotes.value = quotesList
            } catch (e: Exception) {
                val errorMessage = "Error loading quotes: ${e.message}"
                _error.value = errorMessage
                Log.e("QuotesViewModel", errorMessage, e)
            }
        }
    }
}
package com.example.quotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.repository.QuotesRepository
import java.security.InvalidParameterException

class QuotesViewModelFactory(
    private val quotesRepository: QuotesRepository
) :ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesViewModel::class.java)) {
            return QuotesViewModel(quotesRepository) as T
        }
        throw InvalidParameterException("Unable to constructor Register Activity View Model")
    }
}
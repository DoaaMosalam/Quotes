package com.example.quotes.ui.quotesFragment

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opportunity.domain.model.Quotes
import com.opportunity.domain.usecase.QuoteUseCaseData
import com.opportunity.domain.usecase.QuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesUseCase: QuotesUseCase,

    private val quoteUseCaseData: QuoteUseCaseData
) : ViewModel() {

    private val _isLoad = MutableSharedFlow<Boolean>()
    private val _quotes = MutableSharedFlow<List<Quotes>>()
    private val _error = MutableSharedFlow<String>()

    val isLoad: SharedFlow<Boolean> get() = _isLoad
    val quotes: SharedFlow<List<Quotes>> get() = _quotes
    val error: SharedFlow<String> get() = _error

    //this method get all quotes from API then save them into room database.
    fun getAllQuotesFromServiceIntoDatabase() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO){
                    Log.d("quote", "Quotes is: $_quotes")
                    quotesUseCase.getAllQuotes().results
                }
                _quotes.emit(data)

            }catch (e:NetworkErrorException) {
                _error.emit(e.message.toString())
            }
        }
    }

    //=============================================================================================
    //  this method insert quotes to database
    fun insertQuotesToDatabase(quotes: Quotes) {
        viewModelScope.launch {
//            repository.insertQuoteToDatabase(quotes)
            quoteUseCaseData.insertQuoteToData(quotes)
        }
    }

}






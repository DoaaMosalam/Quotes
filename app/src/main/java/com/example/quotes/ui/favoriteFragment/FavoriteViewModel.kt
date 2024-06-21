package com.example.quotes.ui.favoriteFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quotes.util.RequestStatus
import com.opportunity.domain.model.Quotes
import com.opportunity.domain.usecase.QuoteUseCaseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor
    (
            private val quoteUseCaseData: QuoteUseCaseData
//    private val repository: FavoriteRepository
) : ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }

    private val _quotesList = MutableLiveData<RequestStatus<List<Quotes>>>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotesList: LiveData<RequestStatus<List<Quotes>>> get() = _quotesList

    fun getAllQuotesFromDatabase() {
        viewModelScope.launch {
            _isLoad.postValue(true)
            try {
                quoteUseCaseData.getAllQuotesFromData().collect { quotes ->
                    _quotesList.postValue(RequestStatus.Success(quotes))
                }
            } catch (e: Exception) {
                _quotesList.postValue(RequestStatus.Error(e.message.toString()))
            } finally {
                _isLoad.postValue(false)
            }
        }
    }

    fun deleteSpecialQuoteByID(quoteId: String) {
        viewModelScope.launch {
            try {
                quoteUseCaseData.deleteQuoteById(quoteId)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    suspend fun searchQuotes(searchQuery: String): LiveData<List<Quotes>> {
        return quoteUseCaseData.searchQuotes(searchQuery).asLiveData()
    }
}
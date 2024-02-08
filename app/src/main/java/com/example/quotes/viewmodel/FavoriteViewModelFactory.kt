package com.example.quotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.repository.FavoriteRepository
import java.security.InvalidParameterException


class FavoriteViewModelFactory (
    private val favoriteRepository:FavoriteRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw InvalidParameterException("Unable to constructor Register Activity View Model")
    }
}
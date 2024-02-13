package com.example.quotes.domain.util

import android.content.Context
import com.example.quotes.data.local.SharedPreferencesManager

object Credential {
    // URL API QUOTES.
    const val BASE_URL = "https://api.quotable.io/"

    //==================================================================================================
    // KEY PREF.
    const val PREF_KEY = "Quotes"

    private var instance: SharedPreferencesManager? = null

    @Synchronized
    fun getInstance(context: Context): SharedPreferencesManager =
        instance ?: synchronized(this) {
            SharedPreferencesManager(context).apply {
                instance = this
            }
        }

}
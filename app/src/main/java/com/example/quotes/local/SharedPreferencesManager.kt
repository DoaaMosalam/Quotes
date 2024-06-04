package com.example.quotes.local

import android.content.Context
import android.content.SharedPreferences
import com.example.quotes.util.Credential

class SharedPreferencesManager(mCtx: Context) {

    private var sharedPreferences: SharedPreferences =
        mCtx.getSharedPreferences(com.example.quotes.util.Credential.PREF_KEY, Context.MODE_PRIVATE)

    fun saveQuotes(textQuote: String) {
        val editor = sharedPreferences.edit().apply() {
            val uniqueKey = "quote_" + System.currentTimeMillis()
            putString(uniqueKey, textQuote)

        }
        editor.apply()
    }
}
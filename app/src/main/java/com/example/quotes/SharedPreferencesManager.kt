package com.example.quotes

import android.content.Context
import android.content.SharedPreferences

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
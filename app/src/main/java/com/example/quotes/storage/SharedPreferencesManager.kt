package com.example.quotes.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.example.quotes.pojo.Quotes
import com.example.quotes.util.Credential

class SharedPreferencesManager(mCtx: Context) {

    private var sharedPreferences: SharedPreferences =
        mCtx.getSharedPreferences(Credential.PREF_KEY, Context.MODE_PRIVATE)

    fun saveQuotes(textQuote: String) {
        val editor = sharedPreferences.edit().apply() {
            val uniqueKey = "quote_" + System.currentTimeMillis()
            putString(uniqueKey, textQuote)

        }
        editor.apply()
    }
}
package storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import pojo.Quotes
import util.Credential

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
    // get all quotes
    fun getQuotes(): List<Quotes> {
        val quotesList = mutableListOf<Quotes>()
        val allQuotes = sharedPreferences.all
        for (quote in allQuotes) {
            val gson = Gson()
            val json = quote.value.toString()
            val type = object : com.google.gson.reflect.TypeToken<Quotes>() {}.type
            val quotes: Quotes = gson.fromJson(json, type)
            quotesList.add(quotes)
        }
        return quotesList
    }

    fun removeQuote(quote: String) {
        val editor = sharedPreferences.edit()
        editor.remove(quote)
        editor.apply()

    }
}
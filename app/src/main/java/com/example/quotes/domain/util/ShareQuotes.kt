package com.example.quotes.domain.util

import android.content.Context
import android.content.Intent

object ShareQuotes {
    // share quotes when click button share
    fun shareQuote(quote: String, context: Context) {
        // Implement the logic for sharing the quote
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote)
        context.startActivity(Intent.createChooser(shareIntent, "Share Quote"))
    }
}

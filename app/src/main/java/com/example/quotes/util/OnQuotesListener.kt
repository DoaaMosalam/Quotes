package com.example.quotes.util

import com.example.quotes.storage.roomdata.QuotesEntity

interface OnQuotesListener {
        fun setOnButtonQuotesClickListener(listener: OnQuotesListener)
        fun onButtonClick(action: String, quoteEntity: QuotesEntity)

}
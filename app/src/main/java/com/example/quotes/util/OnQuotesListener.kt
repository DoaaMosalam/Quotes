package com.example.quotes.util

import com.example.quotes.storage.roomdata.QuotesEntity

interface OnQuotesListener {
        fun onRemoveClick(quotesEntity: QuotesEntity)
}
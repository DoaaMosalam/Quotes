package com.example.quotes.common

import com.example.quotes.local.QuotesEntity

interface OnQuotesListener {
    fun onRemoveClick(quotesEntity: QuotesEntity)
}
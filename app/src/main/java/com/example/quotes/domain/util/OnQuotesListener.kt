package com.example.quotes.domain.util

import com.example.quotes.data.local.QuotesEntity

interface OnQuotesListener {
    fun onRemoveClick(quotesEntity: QuotesEntity)
}
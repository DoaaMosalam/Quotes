package com.example.quotes.domain.adapter

import com.opportunity.data.local.QuotesEntity

interface OnQuotesListener {
    fun onRemoveClick(quotesEntity: QuotesEntity)
}
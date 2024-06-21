package com.example.quotes.common

import com.opportunity.data.local.QuotesEntity
import com.opportunity.domain.model.Quotes

interface OnQuotesListener {
    fun onRemoveClick(quotes: Quotes)
}
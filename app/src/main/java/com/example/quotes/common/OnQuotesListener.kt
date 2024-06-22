package com.example.quotes.common

import com.opportunity.domain.model.Quotes

interface OnQuotesListener {
    fun onRemoveClick(quotes: Quotes)
}
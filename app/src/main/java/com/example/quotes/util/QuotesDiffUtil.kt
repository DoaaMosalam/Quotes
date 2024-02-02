package com.example.quotes.util

import androidx.recyclerview.widget.DiffUtil
import com.example.quotes.storage.roomdata.QuotesEntity

class QuotesDiffUtil(val mOldQuotes:List<QuotesEntity>,val mNewQuotes:List<QuotesEntity>):DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldQuotes.size

    override fun getNewListSize(): Int = mNewQuotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (
                mOldQuotes[oldItemPosition].id == mNewQuotes[newItemPosition].id
                        && mOldQuotes[oldItemPosition].content == mNewQuotes[newItemPosition].content
                )
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return true
        return mOldQuotes[oldItemPosition].toString() == mNewQuotes[newItemPosition].toString()

    }
}
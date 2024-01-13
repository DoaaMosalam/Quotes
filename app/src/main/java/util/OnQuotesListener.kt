package util

import storage.roomdata.QuotesEntity

interface OnQuotesListener {
        fun setOnButtonQuotesClickListener(listener: OnQuotesListener)
        fun onButtonClick(action: String, quoteEntity: QuotesEntity)

}
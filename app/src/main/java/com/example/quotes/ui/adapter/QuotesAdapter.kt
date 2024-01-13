package com.example.quotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R
import com.example.quotes.databinding.RecyclerViewItemBinding
import pojo.Quotes
import util.ShareQuotes

class QuotesAdapter(private val quotes: List<Quotes>) :
    RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesAdapter.QuotesViewHolder {
        return QuotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuotesAdapter.QuotesViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount(): Int = quotes.size

    // inner class QuotesViewHolder
    inner class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RecyclerViewItemBinding.bind(itemView)
        fun bind(quotes: Quotes) {
            binding.textFavQuote.text = quotes.content
            binding.btnRemoveQuote.setOnClickListener {  }
            binding.btnShareQuoteFV.setOnClickListener {
                ShareQuotes.shareQuote(quotes.content, itemView.context)

            }
        }
    }

}


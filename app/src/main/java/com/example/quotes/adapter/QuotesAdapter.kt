package com.example.quotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.common.OnQuotesListener
import com.example.quotes.databinding.RecyclerViewItemBinding
import com.example.quotes.util.ShareQuotes
import com.opportunity.domain.model.Quotes

class QuotesAdapter(private val listener: OnQuotesListener) :
    RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {
    private lateinit var binding: RecyclerViewItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder()
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class QuotesViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Quotes) {
            binding.textFavQuote.text = item.content
            binding.btnRemoveQuote.setOnClickListener {
                listener.onRemoveClick(item)
            }
            binding.btnShareQuoteFV.setOnClickListener {
                ShareQuotes.shareQuote(item.content, itemView.context)
            }
        }
    }

    private val differUtil = object : DiffUtil.ItemCallback<Quotes>() {
        override fun areItemsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }

    val differ = AsyncListDiffer(this, differUtil)

}






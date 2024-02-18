package com.example.quotes.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.RecyclerViewItemBinding
import com.opportunity.data.local.QuotesEntity
import com.opportunity.domain.util.ShareQuotes

class QuotesAdapter(private val listener: OnQuotesListener) :
    RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {
    private lateinit var binding: RecyclerViewItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesViewHolder {
        binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder()
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    override fun getItemCount(): Int = differ.currentList.size


    // inner class QuotesViewHolder
    inner class QuotesViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuotesEntity) {
            binding.textFavQuote.text = item.content
            binding.btnRemoveQuote.setOnClickListener {
                listener.onRemoveClick(item)
            }
            binding.btnShareQuoteFV.setOnClickListener {
                ShareQuotes.shareQuote(item.content, itemView.context)
            }
        }
    }

    private val differUtil = object : DiffUtil.ItemCallback<QuotesEntity>() {
        override fun areItemsTheSame(oldItem: QuotesEntity, newItem: QuotesEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QuotesEntity, newItem: QuotesEntity): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
    val differ = AsyncListDiffer(this, differUtil)
}






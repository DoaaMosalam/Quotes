package com.example.quotes.ui.favoriteFragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.R
import com.example.quotes.databinding.FragmentFavoriteBinding
import com.example.quotes.adapter.QuotesAdapter
import com.example.quotes.common.BaseFragment
import com.example.quotes.common.OnQuotesListener
import com.example.quotes.util.RequestStatus
import com.example.quotes.util.showSnakeBarError
import com.opportunity.domain.model.Quotes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(), OnQuotesListener { // end class FavoriteFragment
    override fun getLayoutResID() = R.layout.fragment_favorite

    private lateinit var quotesAdapter: QuotesAdapter

    override val viewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // call function to get all quotes from database
        viewModel.getAllQuotesFromDatabase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init RecyclerView
        initRecyclerView()
        // Observe changes in the quote list
        lifecycleScope.launch {
            setUpObserve()
        }

        // call function to search quotes
        searchEditText()
    } // end onViewCreated

    //initiate recyclerview
    private fun initRecyclerView() {
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            quotesAdapter = QuotesAdapter(this@FavoriteFragment)
            adapter = quotesAdapter
        }
    }
    override suspend fun setUpObserve() {
        binding.progressBar.visibility = View.VISIBLE

        viewModel.isLoad.observe(viewLifecycleOwner)
        { isLoad ->
            binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        }
        viewModel.quotesList.observe(viewLifecycleOwner) {
            when (it) {
                is RequestStatus.Success -> {
                    binding.progressBar.isVisible = false
                    quotesAdapter.differ.submitList(it.data)
                }

                is RequestStatus.Error -> {
                    binding.progressBar.isVisible = false
                    view?.showSnakeBarError(it.message)
                }

                is RequestStatus.Waiting -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    override fun onRemoveClick(quotes: Quotes) {
        viewModel.deleteSpecialQuoteByID(quotes.id!!)
        quotesAdapter.notifyDataSetChanged()
    }

    private fun searchEditText() {
        binding.edSearchQuotes.addTextChangedListener { text ->
            lifecycleScope.launch {
                searchQuotes(text.toString())
            }
        }
    }

    private suspend fun searchQuotes(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchQuotes(searchQuery).observe(viewLifecycleOwner) { quotes ->
            quotesAdapter.differ.submitList(quotes)
        }
    }



}


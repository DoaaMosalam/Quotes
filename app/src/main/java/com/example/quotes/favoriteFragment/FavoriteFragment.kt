package com.example.quotes.favoriteFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.R
import com.example.quotes.databinding.FragmentFavoriteBinding
import com.example.quotes.repository.FavoriteRepository
import com.example.quotes.storage.roomdata.QuotesDatabase
import com.example.quotes.ui.adapter.QuotesAdapter
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.OnQuotesListener
import com.example.quotes.util.RequestStatus
import com.example.quotes.viewmodel.FavoriteViewModel
import com.example.quotes.viewmodel.FavoriteViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class FavoriteFragment : Fragment() , OnQuotesListener{
    private lateinit var bindingFv: FragmentFavoriteBinding
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var fViewModel:FavoriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFv = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        
        return bindingFv.root
    } // end onCreateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fViewModel = ViewModelProvider(this, FavoriteViewModelFactory(FavoriteRepository
            (QuotesDatabase.getInstance(requireContext())
            .quotesDatabaseDao())))[FavoriteViewModel::class.java]

//        // call function to get all quotes from database
        fViewModel.getAllQuotesFromDatabase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //init RecyclerView
        initRecyclerView()
        // Observe changes in the quote list
        setUpObserverFavoriteQuotes()
        // call function to search quotes
        searchEditText()
    } // end onViewCreated

    //initiate recyclerview
    private fun initRecyclerView() {
        bindingFv.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            quotesAdapter = QuotesAdapter(this@FavoriteFragment)
            adapter = quotesAdapter
        }
    }

    private fun setUpObserverFavoriteQuotes() {
        bindingFv.progressBar.visibility = View.VISIBLE

        fViewModel.isLoad.observe(viewLifecycleOwner)
        { isLoad ->
            bindingFv.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        }
        fViewModel.quotesList.observe(viewLifecycleOwner){
            when(it){
                is RequestStatus.Success -> {
                    bindingFv.progressBar.isVisible = false
                    quotesAdapter.differ.submitList(it.data)
                }
                is RequestStatus.Error -> {
                    bindingFv.progressBar.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is RequestStatus.Waiting -> {
                    bindingFv.progressBar.isVisible = true
                }
            }
        }
    }
// function to delete a quote from the database
    override fun onRemoveClick(quotesEntity: QuotesEntity) {
        fViewModel.deleteSpecialQuoteByID(quotesEntity.id!!)
        quotesAdapter.notifyDataSetChanged()
    }
    private fun searchEditText() {
       bindingFv.edSearchQuotes.addTextChangedListener{text->
           searchQuotes(text.toString())
       }
    }

    private fun searchQuotes(query: String) {
        val searchQuery = "%$query%"
        fViewModel.searchQuotes(searchQuery).observe(viewLifecycleOwner) { quotes ->
            quotesAdapter.differ.submitList(quotes)
        }
    }

} // end class FavoriteFragment


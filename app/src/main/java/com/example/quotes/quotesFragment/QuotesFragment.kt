package com.example.quotes.quotesFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.R
import com.example.quotes.databinding.FragmentQuotesBinding
import com.example.quotes.pojo.Quotes
import com.example.quotes.repository.QuotesRepository
import com.example.quotes.storage.SharedPreferencesManager
import com.example.quotes.storage.roomdata.QuotesDatabase
import com.example.quotes.storage.roomdata.QuotesEntity
import com.example.quotes.util.ApiService
import com.example.quotes.util.ShareQuotes
import com.example.quotes.viewmodel.QuotesViewModel
import com.example.quotes.viewmodel.QuotesViewModelFactory
import com.google.android.material.snackbar.Snackbar

class QuotesFragment : Fragment(), View.OnClickListener {
    private lateinit var bindingQuotes: FragmentQuotesBinding
    private lateinit var mViewModel: QuotesViewModel

    // set initial full heart red color
    private var isHeartFull = false
    private lateinit var currentQuotes: QuotesEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingQuotes =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quotes, container, false)
        //=======================================
        //call view model provider to get data from api
        mViewModel = ViewModelProvider(
            this, QuotesViewModelFactory(
                QuotesRepository(
                    ApiService.getService(),
                    QuotesDatabase.getInstance(requireContext()).quotesDatabaseDao()
                )
            )
        )[QuotesViewModel::class.java]
        //=======================================
        mViewModel.getAllQuotesFromServiceIntoDatabase()
        setUpObserver()
        //=======================================
        return bindingQuotes.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingQuotes.btnQuotes.setOnClickListener(this)
        bindingQuotes.btnFavorite.setOnClickListener(this)
        bindingQuotes.btnShare.setOnClickListener(this)

        //========================================================================================
        // Add TextWatcher to txtQuotes
        bindingQuotes.txtQuotes.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                // Reset heart color when text changes
                resetHeartColor()
            }
        })

    }

    // Method to reset heart color
    private fun resetHeartColor() {
        isHeartFull = false
        bindingQuotes.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_Quotes -> {
                    observeButtonClick()
                }

                R.id.btn_favorite -> {
                    if (bindingQuotes.txtQuotes.text.isNotEmpty()) {
                        if (this::currentQuotes.isInitialized) {
                            saveFavoriteQuoteToDatabase()
                            changeHeartColor()
                        }
                    } else {
                        Snackbar.make(
                            requireView(),
                            "Please click button quotes first",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                }

                R.id.btn_share -> {
                    ShareQuotes.shareQuote(
                        bindingQuotes.txtQuotes.text.toString(),
                        requireContext()
                    )
                }
            }
        }
    }

    private fun observeButtonClick() {
        displayRandomQuote(mViewModel.quotes.value ?: emptyList())
    }

    //  implementation when click button quotes to get quotes from api
    private fun setUpObserver() {
        // Show progress bar when data fetching starts
        bindingQuotes.progressBar.visibility = View.VISIBLE
        mViewModel.isLoad.observe(viewLifecycleOwner)
        { isLoad ->
            bindingQuotes.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        }

        // Observe the quotes LiveData to update the UI with new quotes when the quotes are ready
        mViewModel.quotes.observe(viewLifecycleOwner) { quotes ->
            // Hide progress bar when data is received
            bindingQuotes.progressBar.visibility = View.GONE
            displayRandomQuote(quotes)

        }
        // Observe the error LiveData to handle any errors
        mViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Hide progress bar when an error occurs
            bindingQuotes.progressBar.visibility = View.GONE
            // Handle error
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }

    //  get quotes random from api
    private fun displayRandomQuote(quotes: List<Quotes>) {
        if (quotes.isNotEmpty()) {
            val shuffledList = quotes.shuffled()
            val randomQuote = shuffledList.last()
            // Store the current quote
            currentQuotes = QuotesEntity(
                _id = randomQuote._id,
                author = randomQuote.author,
                content = randomQuote.content,
                tags = randomQuote.tags,
                authorSlug = randomQuote.authorSlug,
                length = randomQuote.length,
                dateAdded = randomQuote.dateAdded,
                dateModified = randomQuote.dateModified
            )
            // Display the content and author in a custom way
            val formattedQuote = "\"${randomQuote.content}\" \n- ${randomQuote.author}"
            bindingQuotes.txtQuotes.text = formattedQuote
        } else {
            bindingQuotes.txtQuotes.text = buildString {
                append("Quotes No Available")
            }
        }
    }

    //Save data in shared preference
    private fun saveData() {
        SharedPreferencesManager(requireActivity().baseContext).saveQuotes(bindingQuotes.txtQuotes.text.toString())
    }

    // when click button favorite and return to default color after change txt_quotes
    private fun changeHeartColor() {
        isHeartFull = !isHeartFull
        // Change heart color based on isHeartFull
        val heartDrawable = if (isHeartFull) {
            R.drawable.baseline_read_heart
        } else {
            R.drawable.baseline_favorite_border_24
        }
        bindingQuotes.btnFavorite.setImageResource(heartDrawable)
        // Save data in shared preference
        saveData()
    }

    private fun saveFavoriteQuoteToDatabase() {
        if (this::currentQuotes.isInitialized) {
            val currentQuote = QuotesEntity(
                _id = currentQuotes._id,
                id = currentQuotes.id,
                author = currentQuotes.author,
                content = currentQuotes.content,
                tags = currentQuotes.tags,
                authorSlug = currentQuotes.authorSlug,
                length = currentQuotes.length,
                dateAdded = currentQuotes.dateAdded,
                dateModified = currentQuotes.dateModified
            )

            mViewModel.insertQuotesToDatabase(currentQuote)
            Snackbar.make(
                requireView(),
                "Quote saved successfully",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}
package com.example.quotes.ui.quotesFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.quotes.R
import com.example.quotes.SharedPreferencesManager
import com.example.quotes.common.BaseFragment
import com.example.quotes.databinding.FragmentQuotesBinding
import com.example.quotes.util.ShareQuotes
import com.example.quotes.util.showSnakeBarError
import com.example.quotes.util.showSnakeBarMessage
import com.opportunity.data.local.QuotesEntity
import com.opportunity.domain.model.Quotes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding, QuotesViewModel>(), View.OnClickListener {
    override fun getLayoutResID() = R.layout.fragment_quotes
    override val viewModel: QuotesViewModel by viewModels()

    // set initial full heart red color
    private var isHeartFull = false
    private lateinit var currentQuotes: QuotesEntity
    private var allQuotes: List<Quotes> = emptyList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnQuotes.setOnClickListener(this)
        binding.btnFavorite.setOnClickListener(this)
        binding.btnShare.setOnClickListener(this)

        //call view model provider to get data from api
        viewModel.getAllQuotesFromServiceIntoDatabase()
        lifecycleScope.launch {
            setUpObserve()
        }
        //========================================================================================
        // Add TextWatcher to txtQuotes
        binding.txtQuotes.addTextChangedListener(object : TextWatcher {
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
        binding.btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_Quotes -> {
                    displayRandomQuote()
                }

                R.id.btn_favorite -> {
                    if (binding.txtQuotes.text.isNotEmpty()) {
                        if (this::currentQuotes.isInitialized) {
                            saveFavoriteQuoteToDatabase()
                            changeHeartColor()
                        }
                    } else {
                       view?.showSnakeBarMessage("Please click button quotes first")
                        return
                    }
                }

                R.id.btn_share -> {
                    ShareQuotes.shareQuote(
                        binding.txtQuotes.text.toString(),
                        requireContext()
                    )
                }
            }
        }
    }

    //  implementation when click button quotes to get quotes from api
    override suspend fun setUpObserve() {
        // Show progress bar when data fetching starts
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.isLoad.collect { isLoad ->
                binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
            }
        }

        // Observe the quotes SharedFlow to update the UI with new quotes when the quotes are ready
        lifecycleScope.launch {
            viewModel.quotes.collect { quotes ->
                // Hide progress bar when data is received
                binding.progressBar.visibility = View.GONE

                allQuotes = quotes
                displayRandomQuote()
            }
        }
        // Observe the error SharedFlow to handle any errors
        lifecycleScope.launch {
            viewModel.error.collect { errorMessage ->
                // Hide progress bar when an error occurs
                binding.progressBar.visibility = View.GONE
                // Handle error
                view?.showSnakeBarError(errorMessage)
            }
        }

    }
    //  get quotes random from api
    private fun displayRandomQuote() {
        if (allQuotes.isNotEmpty()) {
            val randomQuote = allQuotes.shuffled().first()
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
            val formattedQuote = "\"${randomQuote.content}\" \n- ${randomQuote.author}"
            binding.txtQuotes.text = formattedQuote
        } else {
            binding.txtQuotes.text = buildString {
                append("Quotes No Available")
            }
        }
    }

    //Save data in shared preference
    private fun saveData() {
        SharedPreferencesManager(requireActivity()
            .baseContext)
            .saveQuotes(binding.txtQuotes.text.toString())
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
        binding.btnFavorite.setImageResource(heartDrawable)
        // Save data in shared preference
        saveData()
    }

    private fun saveFavoriteQuoteToDatabase() {
        if (this::currentQuotes.isInitialized) {
            val currentQuote = Quotes(
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

            viewModel.insertQuotesToDatabase(currentQuote)

            view?.showSnakeBarMessage("Quote saved successfully")

        }
    }
}
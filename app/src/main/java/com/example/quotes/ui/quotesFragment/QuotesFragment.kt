package com.example.quotes.ui.quotesFragment

import android.graphics.Color
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
import com.example.quotes.ui.viewmodel.QuotesViewModel
import com.example.quotes.ui.viewmodel.QuotesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import pojo.Quotes
import repository.QuotesRepository
import storage.SharedPreferencesManager
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import util.ApiService
import util.ShareQuotes

class QuotesFragment : Fragment(), View.OnClickListener {
    private lateinit var bindingQuotes: FragmentQuotesBinding
    private lateinit var mViewModel: QuotesViewModel
    // set initial full heart red color
    private var isHeartFull = false
    // set initial background button
    var isWhite = true
    private var randomQuotes: MutableList<Quotes> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingQuotes =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quotes, container, false)
//==================================================================================================
        //call view model provider factory to get data from api
        //call class MyApplication to get data from api
//        val myApplication = (requireActivity().application as MyApplication).quotesRepository
//        val quotesViewModel = ViewModelProvider(this, QuotesViewModelFactory(myApplication))[QuotesViewModel::class.java]
        //=======================================
        val apiQuotes = ApiService.getService()
        val database = QuotesDatabase.getInstance(requireContext())
        val quotesRepository = QuotesRepository(apiQuotes, database)
        val viewModelFactory = QuotesViewModelFactory(quotesRepository)
        mViewModel = ViewModelProvider(this, viewModelFactory)[QuotesViewModel::class.java]
        //==========================================================================================
        //call view model provider to get data from api

//        ViewModelProvider(
//            this,
//            QuotesViewModelFactory(QuotesRepository(ApiService.getService()))
//        )[QuotesViewModel::class.java]

        //---------------------------------------
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
                    if (bindingQuotes.txtQuotes.text.isEmpty()) {
                        Snackbar.make(
                            requireView(),
                            "Please click button quotes first",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                    changeHeartColor()
                }

                R.id.btn_share -> {
                    if (bindingQuotes.txtQuotes.text.isEmpty()) {
                        Snackbar.make(
                            requireView(),
                            "Please click button quotes first",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                    changeBackgroundButtonShare()
                    ShareQuotes.shareQuote(
                        bindingQuotes.txtQuotes.text.toString(),
                        requireContext()
                    )
                }
            }
        }
    }

    private fun observeButtonClick() {
        mViewModel.getAllQuotesFromService()
    }
   //  implementation when click button quotes to get quotes from api
    private fun setUpObserver() {
        mViewModel.isLoad.observe(viewLifecycleOwner)
        { isLoad ->
            if (isLoad) {
                bindingQuotes.progressBar.visibility = View.VISIBLE
            } else {
                bindingQuotes.progressBar.visibility = View.GONE
            }
        }

       // Observe the quotes LiveData to update the UI with new quotes when the quotes are ready
        mViewModel.quotes.observe(viewLifecycleOwner) { quotes ->
            displayRandomQuote(quotes)
        }
        // Observe the error LiveData to handle any errors
        mViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Handle error
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }

    }

    //  get quotes random from api
    private fun displayRandomQuote(quotes: List<Quotes>) {
        if (quotes.isNotEmpty()) {
            val shuffledList = quotes.shuffled()
            val randomQuote = shuffledList.last()
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
    // create method to change color button btn_favorite
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
        saveData()
    }
    //change background button share when click button share
    private fun changeBackgroundButtonShare() {
        isWhite = !isWhite
        if (isWhite) {
            bindingQuotes.btnShare.setBackgroundColor(Color.WHITE)
        } else {
            bindingQuotes.btnShare.setBackgroundColor(resources.getColor(R.color.backgroundColor))
        }
    }
}
package com.example.quotes.ui.quotesFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.quotes.R
import com.example.quotes.databinding.FragmentQuotesBinding
import com.example.quotes.ui.viewmodel.QuotesViewModel
import com.example.quotes.ui.viewmodel.QuotesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import repository.QuotesRepository
import storage.roomdata.QuotesDatabase
import storage.roomdata.QuotesEntity
import storage.SharedPreferencesManager
import util.ShareQuotes
import util.ApiService

class QuotesFragment : Fragment(), View.OnClickListener {
    private lateinit var bindingQuotes: FragmentQuotesBinding
    private lateinit var mViewModel: QuotesViewModel
    // set initial full heart red color
    private var isHeartFull = false
    // set initial background button
    var isWhite = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingQuotes =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quotes, container, false)
        mViewModel = ViewModelProvider(
            this,
            QuotesViewModelFactory(QuotesRepository(ApiService.getService()))
        )[QuotesViewModel::class.java]
        setUpObserver()
        return bindingQuotes.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        bindingQuotes.btnQutoes.setOnClickListener(this)
        bindingQuotes.btnHeart.setOnClickListener(this)
        bindingQuotes.btnShare.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_Qutoes -> {
                    changeBackgroundButtonQuotes()
                    observeButtonClick()
                }

                R.id.btn_Heart -> {
                    if (bindingQuotes.btnQutoes.text.isEmpty() ){
                        Snackbar.make(requireView(), "Please click button quotes first", Snackbar.LENGTH_SHORT).show()
                        return
                    }
                    changeHeartColor()
                }

                R.id.btn_Share -> {
                    if (bindingQuotes.txtQuotes.text.isEmpty()) {
                        Snackbar.make(requireView(), "Please click button quotes first", Snackbar.LENGTH_SHORT).show()
                        return
                    }
                    changeBackgroundButtonShare()
                    ShareQuotes.shareQuote(bindingQuotes.txtQuotes.text.toString(), requireContext())
                }
            }
        }
    }
// implementation when click button quotes to get quotes from api
    private fun setUpObserver() {
        mViewModel.isLoad.observe(viewLifecycleOwner)
        {isLoad->
            if(isLoad) {
                bindingQuotes.progressBar.visibility = View.VISIBLE
            }else  {
                bindingQuotes.progressBar.visibility = View.GONE
            }
        }

        mViewModel.quotes.observe(viewLifecycleOwner) { quotesList ->
            Log.d("QuotesViewModel", "Received quotes: $quotesList")
            // Update your UI with the new list of quotes

            val quotesText = quotesList.joinToString("\n\n") { quote ->
                "${quote.content}\n- ${quote.author}"
            }
                bindingQuotes.txtQuotes.text = quotesText
            }
            // Observe the error LiveData to handle any errors
            mViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
                // Handle error
                Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
    // Observe the error LiveData to handle any errors
    mViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
        // Handle error
        Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
    }
}
    private fun observeButtonClick() {
        // Call loadQuotes() to fetch new quotes when the button is clicked
        mViewModel.loadQuotes()

    }

    private fun insertDataFromDatabase(quotesEntity: QuotesEntity) {
               val database= QuotesDatabase.getInstance(activity?.applicationContext!!)
//        GlobalScope.launch(Dispatchers.IO) {
//            database.quotesDatabaseDao().insertQuote(QuotesEntity("1", "Hello World","Author 1",
//                "Quote 1", listOf("Tag1"), "author_slug_1", 30, "date_added", "date_modified"))
//        }
        GlobalScope.launch(Dispatchers.IO) {
            database.quotesDatabaseDao().insertQuote(quotesEntity)
        }
    }
//Save data in shared preference
    private fun saveData() {
        SharedPreferencesManager(requireActivity().baseContext).saveQuotes(bindingQuotes.txtQuotes.text.toString())
    }

    // this implementation when click button Favorite go to FavoriteFragment used by Navigation component
    private fun onSendQuotes() {
        val action = QuotesFragmentDirections.actionQuotesFragmentToFavoriteFragment()
       Navigation.findNavController(requireView()).navigate(action) }

    // change heart color when click button heart to red color and return to white color when click again and save data in shared preference
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeHeartColor() {
        isHeartFull = !isHeartFull
        // Change heart color or perform other actions here
        val heartDrawable = if (isHeartFull) {
            resources.getDrawable(R.drawable.baseline_read_heart, null)
        } else {
            resources.getDrawable(R.drawable.baseline_favorite_border_24, null)
        }
        bindingQuotes.btnHeart.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            heartDrawable,
            null
        )
        saveData()
        insertDataFromDatabase(
            QuotesEntity("1", "Hello World","Author 1",
            "Quote 1", listOf("Tag1"), "author_slug_1", 30, "date_added", "date_modified")
        )

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
    //change background button quotes when click button quotes
    private fun changeBackgroundButtonQuotes() {
        isWhite = !isWhite
        if (isWhite) {
            bindingQuotes.btnQutoes.setBackgroundColor(Color.WHITE)
        } else {
            bindingQuotes.btnQutoes.setBackgroundColor(resources.getColor(R.color.backgroundColor))
        }
    }
}
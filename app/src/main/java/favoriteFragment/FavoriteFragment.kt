package favoriteFragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.R
import com.example.quotes.databinding.FragmentFavoriteBinding
import com.example.quotes.ui.adapter.QuotesAdapter
import pojo.Quotes
import storage.roomdata.QuotesEntity
import storage.SharedPreferencesManager


class FavoriteFragment : Fragment() {
    private lateinit var bindingFv: FragmentFavoriteBinding
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var quotesList: MutableList<QuotesEntity>
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingFv = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        // init recyclerview
        initRecyclerView()
        return bindingFv.root
    }


    //initiate recyclerview
    private fun initRecyclerView() {
        val favoriteQuotesList = getFavoriteQuotes()
        // val quotesList = sharedPreferences.all
        bindingFv.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            quotesAdapter = QuotesAdapter(favoriteQuotesList)
            adapter = quotesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun getFavoriteQuotes(): List<Quotes> {
        return listOf(
            Quotes(
                "1",
                "Author 1",
                "Quote 1",
                listOf("Tag1"),
                "author_slug_1",
                30,
                "date_added",
                "date_modified"
            ),
            Quotes(
                "2",
                "Author 2",
                "Quote 2",
                listOf("Tag2"),
                "author_slug_2",
                40,
                "date_added",
                "date_modified"
            ),
            Quotes(
                "1",
                "Author 1",
                "Quote 1",
                listOf("Tag1"),
                "author_slug_1",
                30,
                "date_added",
                "date_modified"
            ),
        )
    }
    //create function get data from shared preferences
//    private fun getDataFromShared(): List<Quotes> {
//        val quotesList = sharedPreferences.all
//        val favoriteQuotesList = mutableListOf<Quotes>()
//        for (quote in quotesList) {
//            val quoteObject = Quotes(
//                quote.key,
//                quote.value.toString(),
//                quote.value.toString(),
//                listOf("Tag1"),
//                "author_slug_1",
//                30,
//                "date_added",
//                "date_modified"
//            )
//            favoriteQuotesList.add(quoteObject)
//        }
//        return favoriteQuotesList
//    }



}
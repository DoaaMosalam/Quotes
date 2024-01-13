package com.example.quotes.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.quotes.ui.quotesFragment.QuotesFragment
import favoriteFragment.FavoriteFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    val mainList = listOf("Home","Favorite")
    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position==0)
           QuotesFragment()
        else
            FavoriteFragment()
    }
}
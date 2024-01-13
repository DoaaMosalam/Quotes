package mainFrgment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.quotes.R
import com.example.quotes.databinding.ActivityMainBinding
import com.example.quotes.databinding.FragmentMainBinding
import com.example.quotes.ui.adapter.MainViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var adapter: MainViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        adapter = MainViewPagerAdapter(childFragmentManager, lifecycle)

        mainBinding.apply {
            tabLayout.addTab(mainBinding.tabLayout.newTab().setText("Home"))
            tabLayout.addTab(mainBinding.tabLayout.newTab().setText("Favorite"))

            viewPager2.adapter = adapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        mainBinding.viewPager2.currentItem = tab.position
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mainBinding.tabLayout.selectTab(mainBinding.tabLayout.getTabAt(position))
                }
            })
        }
        return mainBinding.root

    }
}
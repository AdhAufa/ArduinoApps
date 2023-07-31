package com.example.arduinoapps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.arduinoapps.fragment.GraphFragment
import com.example.arduinoapps.fragment.HistoryFragment

class UserViewPagerAdapter(fragmentManager : FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0 -> fragment = HistoryFragment()
            1 -> fragment = GraphFragment()
        }
        return fragment
    }
}
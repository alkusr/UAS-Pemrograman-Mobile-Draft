package com.bassem.weathernow.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bassem.weathernow.fragments.Hours
import com.bassem.weathernow.fragments.Today
import com.bassem.weathernow.fragments.Week

open class PageViewerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {


        return 3
    }

    override fun createFragment(position: Int): Fragment {


        return when (position) {

            0 -> {
                Today()
            }
            1 -> {
                Hours()
            }
            else -> {
                Week()
            }

        }
    }
}
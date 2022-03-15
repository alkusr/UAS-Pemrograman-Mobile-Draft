package com.alkusr.weathernow.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alkusr.weathernow.fragments.currentcity.hourlyWeather.HoursWeather
import com.alkusr.weathernow.fragments.currentcity.currentWeather.CurrentWeather
import com.alkusr.weathernow.fragments.currentcity.weekWeather.Week

open class PageViewerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {


        return 3
    }

    override fun createFragment(position: Int): Fragment {


        return when (position) {

            0 -> {
                CurrentWeather()
            }
            1 -> {
                HoursWeather()
            }
            else -> {
                Week()
            }

        }
    }
}
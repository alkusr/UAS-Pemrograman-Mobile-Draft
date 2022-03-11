package com.bassem.weathernow.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassem.weathernow.R
import com.bassem.weathernow.adapters.WeeklyAdapter
import com.bassem.weathernow.api.API
import com.bassem.weathernow.api.models.apiWeekly.Daily
import com.bassem.weathernow.api.models.apiWeekly.WeeklyxxWeather
import com.bassem.weathernow.databinding.WeekFragmentBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class Week : Fragment(R.layout.week_fragment) {
    var _binding: WeekFragmentBinding? = null
    val binding get() = _binding
    lateinit var lat: String
    lateinit var long: String
    lateinit var weeklyRV: RecyclerView
    lateinit var weatherList: MutableList<Daily>
    lateinit var hourlyAdpter: WeeklyAdapter
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = activity?.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        lat = sharedPreferences?.getString("lat", "30.0444")!!
        long = sharedPreferences.getString("lat", "31.2357")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeekFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weeklyRV = view.findViewById(R.id.RvWeekly)
        weatherList = arrayListOf()
        rvSetup()
        getWeekly(lat, long)
    }

    fun rvSetup() {
        hourlyAdpter = WeeklyAdapter(weatherList, requireContext())
        weeklyRV.adapter = hourlyAdpter
        weeklyRV.setHasFixedSize(true)
        weeklyRV.layoutManager = LinearLayoutManager(context)
    }

    fun getWeekly(lat: String, long: String) {
        val call = API.create().weeklyWeather(lat, long, "minutely,hourly", API_KEY, "7", "metric")
        call.enqueue(object : retrofit2.Callback<WeeklyxxWeather?> {
            override fun onResponse(
                call: retrofit2.Call<WeeklyxxWeather?>,
                response: retrofit2.Response<WeeklyxxWeather?>
            ) {
                val apiList = response.body()?.daily
                if (apiList != null) {
                    for (weather in apiList) {
                        weatherList.add(weather)
                    }
                    hourlyAdpter.notifyDataSetChanged()
                    binding?.weeklyShimmer?.visibility = View.GONE
                    binding?.weeklyLayout?.visibility = View.VISIBLE
                }

            }

            override fun onFailure(call: retrofit2.Call<WeeklyxxWeather?>, t: Throwable) {
            }
        })
    }
}
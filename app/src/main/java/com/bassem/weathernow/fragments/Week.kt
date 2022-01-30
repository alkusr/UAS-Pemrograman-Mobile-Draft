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
import com.bassem.weathernow.api.weekly.Daily
import com.bassem.weathernow.api.weekly.WeeklyxxWeather
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = activity?.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        lat = sharedPreferences?.getString("lat", "")!!
        long = sharedPreferences.getString("lat", "")!!
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
        getCurrentWeather(lat, long)
    }

    fun rvSetup() {
        hourlyAdpter = WeeklyAdapter(weatherList, context!!)
        weeklyRV.adapter = hourlyAdpter
        weeklyRV.setHasFixedSize(true)
        weeklyRV.layoutManager = LinearLayoutManager(context)
    }

    fun getCurrentWeather(lat: String, long: String) {
        val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
        val currentWeatherUrl =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=minutely,hourly&appid=$API_KEY&cnt=7&units=metric"
        val client = OkHttpClient()
        val request = Request.Builder().url(currentWeatherUrl).get().build()
        Thread(Runnable {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.message)
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {
                    var result: String? = null
                    if (!response.isSuccessful) {
                        println("${response.message}=========ERROR")
                    } else {
                        val responseBody: ResponseBody? = response.body
                        if (responseBody != null) {
                            result = responseBody.string()

                        }
                        val gson = GsonBuilder().create()
                        val weeklyWeather = gson.fromJson(result, WeeklyxxWeather::class.java)
                        val apiList = weeklyWeather.daily
                        for (weather in apiList) {
                            weatherList.add(weather)
                        }


                        activity?.runOnUiThread {
                            hourlyAdpter.notifyDataSetChanged()
                              binding?.weeklyShimmer?.visibility = View.GONE
                              binding?.weeklyLayout?.visibility = View.VISIBLE
                        }


                    }

                }
            })
        }).start()

    }
}
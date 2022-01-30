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
import com.bassem.weathernow.adapters.HourlyAdapter
import com.bassem.weathernow.api.apiCurrent.current_weather
import com.bassem.weathernow.api.apiHourly.Hourly
import com.bassem.weathernow.api.apiHourly.HourlyWeather
import com.bassem.weathernow.databinding.HoursFragmentBinding
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class Hours : Fragment(R.layout.hours_fragment) {

    var _binding: HoursFragmentBinding? = null
    val binding get() = _binding
    lateinit var lat: String
    lateinit var long: String
    lateinit var hourlyRV: RecyclerView
    lateinit var weatherList: MutableList<Hourly>
    lateinit var hourlyAdpter: HourlyAdapter

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
        _binding = HoursFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hourlyRV = view.findViewById(R.id.hourlyRv)
        weatherList = arrayListOf()
        rvSetup()

        getCurrentWeather(lat, long)
    }

    fun getCurrentWeather(lat: String, long: String) {
        val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
        val currentWeatherUrl =
            "https://api.openweathermap.org/data/2.5/onecall?lat=$lat&lon=$long&exclude=minutes&appid=$API_KEY&units=metric"
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
                        val currentWeather = gson.fromJson(result, HourlyWeather::class.java)
                        val apiList = currentWeather.hourly
                        for (weather in apiList) {
                            weatherList.add(weather)
                        }


                        activity?.runOnUiThread {
                            hourlyAdpter.notifyDataSetChanged()
                            binding?.hourlyShimmer?.visibility = View.GONE
                            binding?.hourlyLayout?.visibility = View.VISIBLE
                        }


                    }

                }
            })
        }).start()

    }

    fun rvSetup() {
        hourlyAdpter = HourlyAdapter(weatherList, context!!)
        hourlyRV.adapter = hourlyAdpter
        hourlyRV.setHasFixedSize(true)
        hourlyRV.layoutManager = LinearLayoutManager(context)
    }

}
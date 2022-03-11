package com.bassem.weathernow.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bassem.weathernow.R
import com.bassem.weathernow.api.API
import com.bassem.weathernow.api.models.apiCurrent.current_weather
import com.bassem.weathernow.databinding.TodayFragmentBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Today : Fragment(R.layout.today_fragment) {
    var _binding: TodayFragmentBinding? = null
    val binding get() = _binding
    lateinit var fusedlocation: FusedLocationProviderClient
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TodayFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
        binding?.swipe?.setOnRefreshListener {
            getLocation()

        }

    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedlocation = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedlocation.lastLocation.addOnSuccessListener {
            val lat = it.latitude.toString()
            val long = it.longitude.toString()
            saveLocation(lat, long)
            // getCurrentWeather(lat, long)
            getCurrentWeather(lat, long)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatingUI(currentWeather: current_weather) {
        val locale = Locale.US
        val sdf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", locale)
        val timeFormater = DateTimeFormatter.ofPattern("hh:mm a", locale)

        binding?.TvcityName?.text = currentWeather.name
        binding?.TvCurrentTemp?.text = "${currentWeather.main.temp.toInt()} °C"
        val sunRiseSeconds: Int = currentWeather.sys.sunrise
        binding?.TvSunRise?.text =
            Instant.ofEpochSecond(sunRiseSeconds.toLong()).atZone(ZoneId.systemDefault())
                .format(timeFormater).toString()
        val sunSetSeconds: Int = currentWeather.sys.sunset
        binding?.TvSunSet?.text =
            Instant.ofEpochSecond(sunSetSeconds.toLong()).atZone(ZoneId.systemDefault())
                .format(timeFormater).toString()
        binding?.TvDescription?.text = currentWeather.weather[0].description
        binding?.TvWind?.text = "${currentWeather.wind.speed} Km/h"
        binding?.Tvhumidity?.text = "${currentWeather.main.humidity} %"
        binding?.TvPressure?.text = "${currentWeather.main.pressure}"
        binding?.TvFeel?.text = "${currentWeather.main.feels_like.toInt()} °C"
        val timeStamp = currentWeather.dt
        val formatedTime =
            Instant.ofEpochSecond(timeStamp.toLong()).atZone(ZoneId.systemDefault()).format(sdf)
        val displayedUpdate = formatedTime
        println(displayedUpdate)
        binding?.TvLastUpdate?.text = formatedTime.toString()
        val iconCode = currentWeather.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
        Glide.with(this).load(iconUrl).into(binding?.imgIcon!!)

    }

    fun saveLocation(lat: String, long: String) {
        val sharedPreferences = activity?.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("lat", lat)
        editor?.putString("long", long)
        editor?.apply()
    }

    fun getCurrentWeather(lat: String, long: String) {
        val api = API.create().currentWeather("weather", lat, long, API_KEY, "metric")
        api.enqueue(object : retrofit2.Callback<current_weather?> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: retrofit2.Call<current_weather?>,
                response: retrofit2.Response<current_weather?>
            ) {
                val currentWeather = response.body()
                updatingUI(currentWeather!!)
                binding?.shimmerEffect?.visibility = View.GONE
                binding?.currentLayout?.visibility = View.VISIBLE
                binding?.swipe?.isRefreshing = false


            }

            override fun onFailure(call: retrofit2.Call<current_weather?>, t: Throwable) {
            }
        })

    }
}
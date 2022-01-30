package com.bassem.weathernow.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bassem.weathernow.R
import com.bassem.weathernow.apiCurrent.current_weather
import com.bassem.weathernow.databinding.TodayFragmentBinding
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import okhttp3.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.time.Instant
import java.time.ZoneId


class Today : Fragment(R.layout.today_fragment), EasyPermissions.PermissionCallbacks {
    var _binding: TodayFragmentBinding? = null
    val binding get() = _binding
    lateinit var fusedlocation: FusedLocationProviderClient

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
        checkPermission()

    }

    fun hasPermission() =
        EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)

    fun requestPermission() {
        println("REQ============")
        EasyPermissions.requestPermissions(
            this,
            "Weather now needs to access location",
            101,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun checkPermission() {
        if (!hasPermission()) {
            requestPermission()
            println("REQUEST")
        } else {
            getLocation()

        }

    }


    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedlocation = LocationServices.getFusedLocationProviderClient(context!!)
        fusedlocation.lastLocation.addOnSuccessListener {
            val lat = it.latitude.toString()
            val long = it.longitude.toString()
            println("$lat ///////////////////// $long")
            getCurrentWeather(lat, long)
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        //getLocation()
        checkPermission()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    fun getCurrentWeather(lat: String, long: String) {

        val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
        val currentWeatherUrl =
            "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$long&appid=$API_KEY&units=metric"
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
                        val currentWeather = gson.fromJson(result, current_weather::class.java)
                        activity?.runOnUiThread {
                            binding?.shimmerEffect?.visibility = View.GONE
                            binding?.currentLayout?.visibility = View.VISIBLE
                            updatingUI(currentWeather)
                        }


                    }

                }
            })
        }).start()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatingUI(currentWeather: current_weather) {
        binding?.TvcityName?.text = currentWeather.name
        binding?.TvCurrentTemp?.text = "${currentWeather.main.temp.toInt()} °C"
        val sunRiseSeconds: Int = currentWeather.sys.sunrise
        binding?.TvSunRise?.text =
            Instant.ofEpochSecond(sunRiseSeconds.toLong()).atZone(ZoneId.systemDefault())
                .toLocalTime().toString()
        val sunSetSeconds: Int = currentWeather.sys.sunset
        binding?.TvSunSet?.text =
            Instant.ofEpochSecond(sunSetSeconds.toLong()).atZone(ZoneId.systemDefault())
                .toLocalTime().toString()
        binding?.TvDescription?.text = currentWeather.weather[0].description
        binding?.TvWind?.text = "${currentWeather.wind.speed} Km/h"
        binding?.Tvhumidity?.text = "${currentWeather.main.humidity} %"
        binding?.TvPressure?.text = "${currentWeather.main.pressure}"
        binding?.TvFeel?.text="${currentWeather.main.feels_like.toInt()} °C"
        val iconCode = currentWeather.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
        Glide.with(this).load(iconUrl).into(binding?.imgIcon!!)

    }
}
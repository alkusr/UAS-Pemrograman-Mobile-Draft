package com.alkusr.weathernow.fragments.currentcity.currentWeather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alkusr.weathernow.R
import com.alkusr.weathernow.data.models.apiCurrent.current_weather
import com.alkusr.weathernow.databinding.TodayFragmentBinding
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class CurrentWeather : Fragment(R.layout.today_fragment) {
    private var _binding: TodayFragmentBinding? = null
    private val binding get() = _binding
    private var viewModel: ViewModelCurrentWeather? = null
    private val lat = "-3.316694"
    private val long = "114.590111"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TodayFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ViewModelCurrentWeather::class.java]
        viewModel!!.getCurrentWeather(lat,long)
        viewModel!!.currentWeather.observe(viewLifecycleOwner) {
            if (it != null) {
                updatingUI(it)
            } else {

            }
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
        val timeStamp = currentWeather.dt
        val formatedTime =
            Instant.ofEpochSecond(timeStamp.toLong()).atZone(ZoneId.systemDefault()).format(sdf)
        val displayedUpdate = formatedTime
        println(displayedUpdate)
        binding?.TvLastUpdate?.text = formatedTime.toString()
        val iconCode = currentWeather.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
        Glide.with(this).load(iconUrl).into(binding?.imgIcon!!)
        binding?.shimmerEffect?.visibility = View.GONE
        binding?.currentLayout?.visibility = View.VISIBLE

    }


}
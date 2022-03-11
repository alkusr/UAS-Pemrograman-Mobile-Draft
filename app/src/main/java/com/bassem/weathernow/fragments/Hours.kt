package com.bassem.weathernow.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassem.weathernow.R
import com.bassem.weathernow.adapters.HourlyAdapter
import com.bassem.weathernow.api.API
import com.bassem.weathernow.api.models.apiHourly.Hourly
import com.bassem.weathernow.api.models.apiHourly.HourlyWeather
import com.bassem.weathernow.databinding.HoursFragmentBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class Hours : Fragment(R.layout.hours_fragment) {

    var _binding: HoursFragmentBinding? = null
    val binding get() = _binding
    lateinit var lat: String
    lateinit var long: String
    lateinit var hourlyRV: RecyclerView
    lateinit var weatherList: MutableList<Hourly>
    lateinit var hourlyAdpter: HourlyAdapter
    var dots: DotsIndicator? = null
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"


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
        getHourly(lat, long)
        dots = view.findViewById(R.id.dots_indicator)
    }


    fun getHourly(lat: String, long: String) {
        val apiCall = API.create().hourlyWeather(lat, long, "minutes", API_KEY, "metric")
        apiCall.enqueue(object : retrofit2.Callback<HourlyWeather?> {
            override fun onResponse(
                call: retrofit2.Call<HourlyWeather?>,
                response: retrofit2.Response<HourlyWeather?>
            ) {
                val resultList = response.body()?.hourly
                if (resultList != null) {
                    for (weather in resultList) {
                        weatherList.add(weather)
                    }
                }
                hourlyAdpter.notifyDataSetChanged()
                binding?.hourlyShimmer?.visibility = View.GONE
                binding?.hourlyLayout?.visibility = View.VISIBLE


            }

            override fun onFailure(call: retrofit2.Call<HourlyWeather?>, t: Throwable) {
            }
        })
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun rvSetup() {
        hourlyAdpter = HourlyAdapter(weatherList, requireContext())
        hourlyRV.apply {
            adapter = hourlyAdpter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    println("Scrolling. State......")
                    dots?.visibility = View.INVISIBLE
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    println("Scrolling.......")
                    dots?.visibility = View.INVISIBLE
                }
            })
        }
    }
}


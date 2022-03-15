package com.alkusr.weathernow.fragments.currentcity.weekWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alkusr.weathernow.R
import com.alkusr.weathernow.adapters.WeeklyAdapter
import com.alkusr.weathernow.data.models.apiWeekly.Daily
import com.alkusr.weathernow.databinding.WeekFragmentBinding

class Week : Fragment(R.layout.week_fragment) {
    var _binding: WeekFragmentBinding? = null
    val binding get() = _binding
    private val lat = "-3.316694"
    private val long = "114.590111"
    lateinit var weeklyRV: RecyclerView
    lateinit var weatherList: MutableList<Daily>
    lateinit var hourlyAdpter: WeeklyAdapter
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
    var viewModel: ViewModelWeekWeather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        viewModel = ViewModelProvider(this)[ViewModelWeekWeather::class.java]
        viewModel!!.getWeek(lat, long)
        viewModel!!.weekList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding?.weeklyLayout?.visibility = View.VISIBLE
                binding?.weeklyShimmer?.visibility = View.GONE
                hourlyAdpter.addList(it)
            }
        })
        rvSetup()

    }

    fun rvSetup() {
        hourlyAdpter = WeeklyAdapter(weatherList, requireContext())
        weeklyRV.adapter = hourlyAdpter
        weeklyRV.setHasFixedSize(true)
        weeklyRV.layoutManager = LinearLayoutManager(context)
    }


}
package com.alkusr.weathernow.fragments.currentcity.hourlyWeather

import android.annotation.SuppressLint
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
import com.alkusr.weathernow.adapters.HourlyAdapter
import com.alkusr.weathernow.data.models.apiHourly.Hourly
import com.alkusr.weathernow.databinding.HoursFragmentBinding

class HoursWeather : Fragment(R.layout.hours_fragment) {

    var _binding: HoursFragmentBinding? = null
    val binding get() = _binding
    private val lat = "-3.316694"
    private val long = "114.590111"
    lateinit var hourlyRV: RecyclerView
    lateinit var weatherList: MutableList<Hourly>
    lateinit var hourlyAdpter: HourlyAdapter
    var viewModel: ViewModelHoursWeather? = null

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
        viewModel = ViewModelProvider(this)[ViewModelHoursWeather::class.java]
        viewModel!!.getHourly(lat,long)
        viewModel!!.hoursList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                hourlyAdpter.addList(it)
                binding?.hourlyShimmer?.visibility = View.GONE
                binding?.hourlyLayout?.visibility = View.VISIBLE
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
        }
    }
}


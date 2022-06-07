package com.alkusr.weathernow.fragments.currentcity.hourlyWeather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alkusr.weathernow.data.api.ServiceAPI
import com.alkusr.weathernow.data.models.apiHourly.Hourly
import com.alkusr.weathernow.data.models.apiHourly.HourlyWeather

class ViewModelHoursWeather(app: Application) : AndroidViewModel(app) {
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
    val hoursList = MutableLiveData<List<Hourly>>()



    fun getHourly(lat: String, long: String) {
        val apiCall = ServiceAPI.create().hourlyWeather(lat, long, "minutes", API_KEY, "metric")
        apiCall.enqueue(object : retrofit2.Callback<HourlyWeather?> {
            override fun onResponse(
                call: retrofit2.Call<HourlyWeather?>,
                response: retrofit2.Response<HourlyWeather?>
            ) {
                val resultList = response.body()?.hourly
                if (resultList != null) {
                    hoursList.postValue(resultList!!)
                }


            }

            override fun onFailure(call: retrofit2.Call<HourlyWeather?>, t: Throwable) {
            }
        })
    }


}
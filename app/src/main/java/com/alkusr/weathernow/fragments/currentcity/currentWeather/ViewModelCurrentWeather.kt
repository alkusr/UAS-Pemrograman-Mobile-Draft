package com.alkusr.weathernow.fragments.currentcity.currentWeather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alkusr.weathernow.data.api.ServiceAPI
import com.alkusr.weathernow.data.models.apiCurrent.current_weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelCurrentWeather(application: Application) :
    AndroidViewModel(application) {


    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
    val currentWeather = MutableLiveData<current_weather>()



    fun getCurrentWeather(valueLat: String, valueLong: String) {
        val api = ServiceAPI.create()
            .currentWeather("weather", valueLat, valueLong, API_KEY, "metric")
        api.enqueue(object : Callback<current_weather?> {
            override fun onResponse(
                call: Call<current_weather?>,
                response: Response<current_weather?>
            ) {
                currentWeather.postValue(response.body())
                println("=====================${response.code()}")
            }

            override fun onFailure(call: Call<current_weather?>, t: Throwable) {
               println("${t.message}     Fai")
            }
        })
    }






}
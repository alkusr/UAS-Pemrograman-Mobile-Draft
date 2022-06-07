package com.alkusr.weathernow.fragments.currentcity.weekWeather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alkusr.weathernow.data.api.ServiceAPI
import com.alkusr.weathernow.data.models.apiWeekly.Daily
import com.alkusr.weathernow.data.models.apiWeekly.WeeklyxxWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelWeekWeather(app: Application) : AndroidViewModel(app) {
    val API_KEY = "b9eef1568d8d1cea6bb9549c7bda1bb9"
    val weekList = MutableLiveData<List<Daily>>()


    fun getWeek(lat: String, long: String) {

        val api =
            ServiceAPI.create().weeklyWeather(lat, long, "minutely,hourly", API_KEY, "7", "metric")
        api.enqueue(object : Callback<WeeklyxxWeather?> {
            override fun onResponse(
                call: Call<WeeklyxxWeather?>,
                response: Response<WeeklyxxWeather?>
            ) {
                if (response.isSuccessful) {
                    if (response != null) {
                        weekList.postValue(response.body()!!.daily)
                    }
                }
            }

            override fun onFailure(call: Call<WeeklyxxWeather?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }


}
package com.alkusr.weathernow.data.api

import com.alkusr.weathernow.data.models.apiCurrent.current_weather
import com.alkusr.weathernow.data.models.apiHourly.HourlyWeather
import com.alkusr.weathernow.data.models.apiWeekly.WeeklyxxWeather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("weather")
    fun currentWeather(
        @Query("weather") weather: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") KEY: String,
        @Query("units") unit: String
    ): Call<current_weather>

    @GET("onecall")
    fun hourlyWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") ex: String,
        @Query("appid") KEY: String,
        @Query("units") unit: String
    ): Call<HourlyWeather>

    @GET("onecall")
    fun weeklyWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") ex: String,
        @Query("appid") KEY: String,
        @Query("cnt") cnt: String,
        @Query("units") unit: String
    ): Call<WeeklyxxWeather>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create(): ServiceAPI {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()

            return retrofit.create(ServiceAPI::class.java)
        }

    }

}
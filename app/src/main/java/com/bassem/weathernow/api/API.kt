package com.bassem.weathernow.api

import com.bassem.weathernow.api.models.apiCurrent.current_weather
import com.bassem.weathernow.api.models.apiHourly.HourlyWeather
import com.bassem.weathernow.api.models.apiWeekly.WeeklyxxWeather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {


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
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create(): API {
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build()

            return retrofit.create(API::class.java)
        }

    }

}
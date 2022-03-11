package com.bassem.weathernow.api.models.apiHourly

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Double,
    val pop: Double,
    val pressure: Double,
    val temp: Double,
    val uvi: Double,
    val visibility: Double,
    val weather: List<WeatherXX>,
    val wind_deg: Double,
    val wind_gust: Double,
    val wind_speed: Double
)
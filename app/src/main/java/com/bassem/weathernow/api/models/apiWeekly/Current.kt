package com.bassem.weathernow.api.models.apiWeekly

data class Current(
    val clouds: Double,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Double,
    val pressure: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Double,
    val wind_speed: Double
)
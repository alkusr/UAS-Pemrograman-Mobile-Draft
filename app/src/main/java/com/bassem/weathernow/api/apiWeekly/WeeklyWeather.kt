package com.bassem.weathernow.api.apiWeekly

data class WeeklyWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Weekly>,
    val message: Int
)
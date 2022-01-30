package com.bassem.weathernow.apiCurrent

data class current_weather(
    val base: String,
    val clouds: Clouds,
    val cod: Double,
    val coord: Coord,
    val dt: Double,
    val id: Double,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Double,
    val weather: List<Weather>,
    val wind: Wind
)
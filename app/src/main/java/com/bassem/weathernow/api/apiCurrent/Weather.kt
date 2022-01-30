package com.bassem.weathernow.api.apiCurrent

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
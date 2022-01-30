package com.bassem.weathernow.api.apiHourly

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
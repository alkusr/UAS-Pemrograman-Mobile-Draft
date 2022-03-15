package com.alkusr.weathernow.data.models.apiHourly

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
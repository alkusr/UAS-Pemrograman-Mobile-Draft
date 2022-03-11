package com.bassem.weathernow.api.models.apiWeekly

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
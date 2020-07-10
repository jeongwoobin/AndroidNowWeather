package com.example.nowweather.data

import kotlin.collections.ArrayList

data class DayWeather (
    val current: Current?,
    val daily: ArrayList<Daily>?
) {
    data class Current (
        val temp: Double?,
        val dt: Long?,
        val weather: ArrayList<Weather>?
    ) {
        data class Weather (
            val main: String?,
            val description: String?,
            val icon: String?,
            val id: Int?
        )
    }

    data class Daily (
        val temp: Temp?,
        val dt: Long?,
        val weather: ArrayList<Weather>?
    ) {
        data class Temp (
            val min: Double?,
            val max: Double?
        )
        data class Weather (
            val description: String?,
            val icon: String?,
            val id: Int?
        )
    }
}
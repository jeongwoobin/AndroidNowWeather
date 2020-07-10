package com.example.nowweather.service

import com.example.nowweather.data.DayWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("data/2.5/onecall")
    fun getHourly(
        @Query("appid") appKey: String?,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String?,
        @Query("exclude") exclude: String?
    ): retrofit2.Call<DayWeather>?
}
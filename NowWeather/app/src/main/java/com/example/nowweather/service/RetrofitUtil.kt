package com.example.nowweather.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {
    companion object {
        val BASEURL: String = "https://api.openweathermap.org/"
        val APPKEY: String? = "552e9dc278172ec67e26ee765d676cae"
        val UNITS: String? = "metric"
        val EXCLUDE: String? = "hourly,minutely"

        fun getService(): RetrofitService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RetrofitService::class.java)
        }
    }
}
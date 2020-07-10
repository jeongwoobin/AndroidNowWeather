package com.example.nowweather.service

import com.example.nowweather.R

class ImageUtil() {
    companion object {
        fun transfer(code: Int): Int {
            val id = code/100

            when (id) {
                8 -> {
                    when (code) {
                        800 -> return R.drawable.icon_clear
                        801, 802 -> return R.drawable.icon_light_cloud
                        803, 804 -> return R.drawable.icon_cloud
                    }
                }
                5 -> {
                    return when (code) {
                        500, 501 -> R.drawable.icon_rain
                        else -> R.drawable.icon_heavy_rain
                    }
                }
                else -> {
                    when(id) {
                        2 -> return R.drawable.icon_thunder
                        3 -> return R.drawable.icon_drizzle
                        6 -> return R.drawable.icon_snow
                        7 -> return R.drawable.icon_fog
                    }
                }
            }

            return 0
        }
    }
}
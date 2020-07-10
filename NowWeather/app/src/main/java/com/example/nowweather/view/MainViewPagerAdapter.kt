package com.example.nowweather.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.nowweather.data.DayWeather

class MainViewPagerAdapter (fragmentManager: FragmentManager, val item: DayWeather?, val city: String) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val num = 2

    override fun getCount(): Int {
        return num
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DailyFragment(item, city)
            1 -> WeeklyFragment(item)
            else -> WeeklyFragment(item)
        }
    }
}
package com.example.nowweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.nowweather.R
import com.example.nowweather.data.DayWeather
import com.example.nowweather.service.ImageUtil
import kotlinx.android.synthetic.main.fragment_daily.*

class DailyFragment(val item: DayWeather?, val city: String) : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setView() {
        if (item != null) {
            tvCity.text = city
            curtemp.text = item.current?.temp.toString() + "\u00b0C"
            weatherDes.text = item.current?.weather?.get(0)?.description.toString()
            mintemp.text = item.daily?.get(0)?.temp?.min.toString() + "\u00b0C"
            maxtemp.text = item.daily?.get(0)?.temp?.max.toString() + "\u00b0C"
            Glide.with(activity!!).load(ImageUtil.transfer(item.current!!.weather!![0].id!!)).into(weatherImg)
        }
    }

}
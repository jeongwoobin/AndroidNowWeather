package com.example.nowweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nowweather.R
import com.example.nowweather.data.DayWeather
import kotlinx.android.synthetic.main.fragment_weekly.*

class WeeklyFragment(val item: DayWeather?) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun setRecycler() {
        weatherRecycler.layoutManager = LinearLayoutManager(activity)
        weatherRecycler.adapter = WeeklyRvAdapter(activity!!, item?.daily!!)
    }


}
package com.example.nowweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nowweather.R
import com.example.nowweather.data.DayWeather
import com.example.nowweather.service.ImageUtil
import kotlinx.android.synthetic.main.item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeeklyRvAdapter(val context: Context, val data: ArrayList<DayWeather.Daily>) :
    RecyclerView.Adapter<WeeklyRvAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) = ItemViewHolder(viewGroup)

    override fun onBindViewHolder(holder: ItemViewHolder, i: Int) {
        data[i].let { item ->
            with(holder) {
                val dateForm = SimpleDateFormat("MM월 dd일")
                val timeMillis = item.dt?.times(1000)
                val timeInDate = Date(timeMillis!!)
                val time = dateForm.format(timeInDate)
                tvWeekDate.text = time
                tvMinTemp.text = item.temp?.min.toString()
                tvMaxTemp.text = item.temp?.max.toString()
                tvWeekDes.text = item.weather?.get(0)?.description.toString()
                Glide.with(context).load(ImageUtil.transfer(item.weather!![0].id!!)).into(imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (data.isNotEmpty()) {
            data.size
        } else {
            0
        }
    }

    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item, parent, false)) {
        val imageView = itemView.imageView!!
        val tvWeekDate = itemView.tvweekdate!!
        val tvWeekDes = itemView.tvweekdes!!
        val tvMinTemp = itemView.tvweekmintemp!!
        val tvMaxTemp = itemView.tvweekmaxtemp!!
    }
}
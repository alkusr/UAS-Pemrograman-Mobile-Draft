package com.bassem.weathernow.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bassem.weathernow.R
import com.bassem.weathernow.api.apiHourly.Hourly
import com.bumptech.glide.Glide
import java.time.Instant
import java.time.ZoneId

class HourlyAdapter(val weatherList: MutableList<Hourly>,val context:Context) :
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.timeHourly)
        val temp = itemView.findViewById<TextView>(R.id.tempHourly)
        val icon=itemView.findViewById<ImageView>(R.id.iconHourly)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.hourly_preview, parent, false)
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        val timeMill = weather.dt
        val iconCode=weather.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"

        val formatedTime = Instant.ofEpochSecond(timeMill.toLong()).atZone(ZoneId.systemDefault()).toLocalTime().toString()
        holder.time.text = formatedTime.toString()
        holder.temp.text = "${weather.temp.toInt()} °C"
        Glide.with(context).load(iconUrl).into(holder.icon)


    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}
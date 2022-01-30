package com.bassem.weathernow.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bassem.weathernow.R
import com.bassem.weathernow.api.apiHourly.Hourly
import java.time.Instant
import java.time.ZoneId

class HourlyAdapter(val weatherList: MutableList<Hourly>) :
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.timeHourly)
        val temp = itemView.findViewById<TextView>(R.id.tempHourly)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.hourly_preview, parent, false)
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        val timeMill = weather.time
       // holder.time.text = Instant.ofEpochSecond(timeMill.toLong()).atZone(ZoneId.systemDefault()).toLocalTime().toString()
        holder.time.text=timeMill.toString()
        holder.temp.text = "${weather.temp.toInt()} °C"
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}
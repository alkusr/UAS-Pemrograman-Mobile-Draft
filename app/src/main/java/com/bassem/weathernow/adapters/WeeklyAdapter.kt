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
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class WeeklyAdapter(val weatherList: MutableList<com.bassem.weathernow.api.weekly.Daily>, val context:Context) :
    RecyclerView.Adapter<WeeklyAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time = itemView.findViewById<TextView>(R.id.weeklyDate)
        val tempMax = itemView.findViewById<TextView>(R.id.weeklyMax)
        val tempMin = itemView.findViewById<TextView>(R.id.weeklyMin)
        val desc = itemView.findViewById<TextView>(R.id.weeklyDesc)


        val icon=itemView.findViewById<ImageView>(R.id.weeklyIcon)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weekly_preview, parent, false)
        return ViewHolder(v)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        val timeMill = weather.dt
        val iconCode=weather.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/w/$iconCode.png"
        val cal = Calendar.getInstance()
        val formatedTime = Instant.ofEpochSecond(timeMill.toLong()).atZone(ZoneId.systemDefault()).toLocalDate().toString()
        val locale = Locale.US
        val sdf = SimpleDateFormat("yyyy-MM-dd", locale)
        val calDate: Date = sdf.parse(formatedTime)
        cal.time = calDate
        val dayNumber: Int = cal.get(Calendar.DAY_OF_WEEK)
        val daysList = listOf<String>(
            "SUNDAY",
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY"
        )
        val dayName = daysList[dayNumber - 1]



        holder.time.text = dayName
        holder.tempMax.text="${weather.temp.max.toInt()} °C"
        holder.tempMin.text="${weather.temp.min.toInt()} °C"
        holder.desc.text=weather.weather[0].description
        Glide.with(context).load(iconUrl).into(holder.icon)


    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}
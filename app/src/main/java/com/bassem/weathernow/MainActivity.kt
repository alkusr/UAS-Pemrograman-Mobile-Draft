package com.bassem.weathernow

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bassem.weathernow.adapters.PageViewerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    lateinit var pager:ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager=findViewById(R.id.viewpager)
        pager.adapter=PageViewerAdapter(this.supportFragmentManager,lifecycle)
        val dotsIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        dotsIndicator.setViewPager2(pager)






    }

}
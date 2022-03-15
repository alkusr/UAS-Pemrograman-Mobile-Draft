package com.alkusr.weathernow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            goToContainer()
        }, 1200)


    }

    fun goToContainer() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}
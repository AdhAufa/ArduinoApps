package com.example.arduinoapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.arduinoapps.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        start()
        supportActionBar?.hide()
    }

    fun start(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java).also { finish() })
        },3000)
    }
}
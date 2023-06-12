package com.example.arduinoapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arduinoapps.databinding.ActivityProfileBinding
import com.example.arduinoapps.webservices.Constants

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(
            layoutInflater
        )
        logout()
        setContentView(binding.root)
    }

    private fun logout(){
        binding.BtnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).also {
                Constants.clearToken(this)

            }
            startActivity(intent)
            finish()
        }
    }
}
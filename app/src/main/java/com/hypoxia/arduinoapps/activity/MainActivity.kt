package com.hypoxia.arduinoapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hypoxia.arduinoapps.R
import com.hypoxia.arduinoapps.databinding.ActivityMainBinding
import com.hypoxia.arduinoapps.fragment.DetectionFragment
import com.hypoxia.arduinoapps.fragment.HomeFragment
import com.hypoxia.arduinoapps.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        bottomNavigation()
        setCurrentFragment(HomeFragment())
    }
    private fun bottomNavigation(){
        binding.BottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.icHome -> setCurrentFragment(HomeFragment())
                R.id.icDetect -> setCurrentFragment(DetectionFragment())
                R.id.icProfile -> setCurrentFragment(ProfileFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FrameLayout, fragment)
            commit()
        }
    }
}
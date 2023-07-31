package com.hypoxia.arduinoapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hypoxia.arduinoapps.adapter.UserViewPagerAdapter
import com.hypoxia.arduinoapps.databinding.ActivityProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewPagerAdapter: UserViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setupViewPager()
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.title = "Detail Profile"
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun setupViewPager(){
        userViewPagerAdapter = UserViewPagerAdapter(supportFragmentManager, lifecycle)
        with(binding){
            ViewPager.adapter = userViewPagerAdapter

            TabLayoutMediator(Tab, ViewPager){ tab, position ->
                when(position){
                    0 -> tab.text = "History"
                    1 -> tab.text = "Graph"
                }

            }.attach()
        }
    }


}
package com.example.arduinoapps.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.example.arduinoapps.databinding.ActivityDetailDisorderBinding
import com.example.arduinoapps.model.Disorder


class DetailDisorderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDisorderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDisorderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        back()
        getDetailDisorder()
    }

    private fun back(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getDetailDisorder(){
        val bundle: Bundle? = intent.extras
        bundle.apply {
            val disorder  = this?.getSerializable("disorder") as Disorder?
            println("Disorder : ${disorder}")
            if(disorder != null){
                binding.TitleDetail.text = disorder.title
                htmlParser(disorder.content!!)
                Glide.with(this@DetailDisorderActivity)
                    .load(disorder.image)
                    .into(binding.ImageDetail)


            }
        }
    }

    private fun htmlParser(content : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.ContentDetail.text = Html.fromHtml("$content", Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding.ContentDetail.text = Html.fromHtml("$content")
        }
    }
}
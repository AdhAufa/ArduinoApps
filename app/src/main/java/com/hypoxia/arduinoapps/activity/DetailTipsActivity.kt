package com.hypoxia.arduinoapps.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.hypoxia.arduinoapps.databinding.ActivityDetailTipsBinding
import com.hypoxia.arduinoapps.model.Tips

class DetailTipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        back()
        getTipsDetail()
    }

    private fun back(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun getTipsDetail(){
        val bundle: Bundle? = intent.extras
        bundle.apply {
            val tips  = this?.getSerializable("tips") as Tips?
            if(tips != null){
                binding.TitleDetail.text = tips.title
                htmlParser(tips.content!!)
                Glide.with(this@DetailTipsActivity)
                    .load(tips.image)
                    .into(binding.ImageDetail)
            }
        }
    }
    private fun htmlParser(content : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            binding.ContentDetail.text = Html.fromHtml("$content",Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding.ContentDetail.text = Html.fromHtml("$content")
        }
    }
}
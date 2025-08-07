package com.example.autismapp.ui.maths

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityLearnNumbersBinding

class LearnNumbersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnNumbersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playVideo()
    }

    private fun playVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.sayilariogreniyorum}")
        binding.videoView2.setVideoURI(videoUri)
        binding.videoView2.setOnCompletionListener {
            // Video tamamlandığında bu kod çalışır
            val intent = Intent(this, CategoriesMathsActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.videoView2.start()
    }
}

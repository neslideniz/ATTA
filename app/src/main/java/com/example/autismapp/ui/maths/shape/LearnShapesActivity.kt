package com.example.autismapp.ui.maths.shape

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityLearnShapesBinding
import com.example.autismapp.ui.maths.CategoriesMathsActivity

class LearnShapesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnShapesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_learn_shapes)
        binding = ActivityLearnShapesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playVideo()
    }
    private fun playVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.shape}")
        binding.videoView2.setVideoURI(videoUri)
        binding.videoView2.setOnCompletionListener {
            // Video tamamlandığında bu kod çalışır
            val intent = Intent(this, CategoriesShapesActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.videoView2.start()
    }
}
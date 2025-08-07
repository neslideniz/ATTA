package com.example.autismapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityCategoriesBinding
import com.example.autismapp.ui.maths.CategoriesMathsActivity

class CategoriesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_categories)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.handWashingButton.setOnClickListener { handWashingActivity() }
        binding.emotionMatchingButton.setOnClickListener { emotionMatchingActivity()
        binding.mathsButton.setOnClickListener { mathsActivity() }}

    }
    fun handWashingActivity(){ // El yıkama etkınlıgıne gıder
        startWelcomeActivity("Selam! Temizlik ve hijyen için el yıkamanın doğru adımlarını keşfedeceğiz, başlıyoruz!", R.drawable.handwashing, HandWashingActivity::class.java)
    }

    fun emotionMatchingActivity(){ // Duygu eşlestırme oyununa gıder
        startWelcomeActivity("Merhaba! Şimdi duyguları keşfetme ve eşleştirme zamanı, eğlenmeye hazır mısın?", R.drawable.feeling, EmotionMatchingActivity::class.java)
    }
    fun mathsActivity(){ // matematik etkınlıgıne gider
        startWelcomeActivity("Merhaba! Şimdi birlikte eğlenceli matematik etkinliklerine başlayacağız, hazır mısın?", R.drawable.mathsphoto, CategoriesMathsActivity::class.java)
    }
    private fun startWelcomeActivity(welcomeText: String, imageResId: Int, nextActivityClass: Class<*>) {
        val intent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra("WELCOME_TEXT", welcomeText)
            putExtra("IMAGE_RES_ID", imageResId)
            putExtra("NEXT_ACTIVITY", nextActivityClass)
        }
        startActivity(intent)
    }
}
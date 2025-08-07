package com.example.autismapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityWelcomeBinding
import java.util.Locale


class WelcomeActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityWelcomeBinding
    private var textToSpeech: TextToSpeech? = null
    private lateinit var welcomeText: String
    private lateinit var nextActivityClass: Class<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent'ten gelen verileri alın
        welcomeText = intent.getStringExtra("WELCOME_TEXT") ?: "Hoşgeldiniz"
        val imageResId = intent.getIntExtra("IMAGE_RES_ID", R.drawable.feeling)
        nextActivityClass = intent.getSerializableExtra("NEXT_ACTIVITY") as Class<*>

        // Görseli ve metni ayarlayın
        binding.imageView.setImageResource(imageResId)
        binding.textView2.text = welcomeText

        // TextToSpeech'i başlat
        textToSpeech = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Türkçe dil desteklenmiyor", Toast.LENGTH_SHORT).show()
            } else {
                // Sesi daha neşeli yapmak için perdeyi ve hızı ayarlayın
                textToSpeech?.setPitch(1.5f) // 1.0 normal perde, 1.5 daha yüksek perde
                textToSpeech?.setSpeechRate(1.3f) // 1.0 normal hız, 1.3 daha hızlı

                speak(welcomeText, "welcome_message")
            }
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}

                override fun onDone(utteranceId: String?) {
                    if (utteranceId == "welcome_message") {
                        // İlgili aktiviteye geçiş yap
                        val intent = Intent(applicationContext, nextActivityClass)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onError(utteranceId: String?) {}
            })
        } else {
            Toast.makeText(this, "Text-to-Speech başlatılamadı", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech?.shutdown()
    }

    private fun speak(text: String, utteranceId: String = "") {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }
}

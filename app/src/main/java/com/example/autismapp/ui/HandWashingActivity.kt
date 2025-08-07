package com.example.autismapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityHandWashingBinding
import java.util.Locale

class HandWashingActivity : AppCompatActivity(),  TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityHandWashingBinding
    private var currentStep = 0
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandWashingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TextToSpeech'i başlat
        textToSpeech = TextToSpeech(this, this)

        startStep()
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Türkçe dil desteklenmiyor", Toast.LENGTH_SHORT).show()
            } else {
                // Toast.makeText(this, "Text-to-Speech başlatıldı", Toast.LENGTH_SHORT).show()
            }
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {}
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
    private fun showStepDescription(step: String) {
        val lowercaseStep = step.toLowerCase(Locale.getDefault()) // Metni küçük harflere dönüştür
        binding.stepDescriptionTextView.text = step // Metni ekranda büyük harflerle göster
        speak(lowercaseStep) // Metni seslendirirken küçük harflerle gönder
    }

    private fun startStep(){
// el yıkama adımları: elleri ıslatma - sabunlama - ovalama - durulama - kurulama bu sıraya gore animasyonları ekle

        when (currentStep) {
            0 -> { // elleri ıslatma
                showStepDescription("ELLERİNİ ISLATMA")
                playVideo(R.raw.wetting_hands) // indirilen videoları başlıklara göre eklendi
                startTimer(6) {
                    currentStep++
                    startStep()
                }
            }
            1 -> { // sabunlama
                showStepDescription("SABUNLAMA")
                playVideo(R.raw.soap)
                startTimer(6) {
                    currentStep++
                    startStep()
                }
            }
            2 -> { // ovalama
                showStepDescription("OVALAMA")
                playVideo(R.raw.rubbing)
                startTimer(30) {
                    currentStep++
                    startStep()
                }
            }
            3 -> { // durulama
                showStepDescription("DURULAMA")
                playVideo(R.raw.rinsing)
                startTimer(11){
                    currentStep++
                    startStep()
                }
            }
            4 -> { // kurulama
                showStepDescription("KURULAMA")
                playVideo(R.raw.drying)
                // Kurulama videosunu belirli bir süre oynatma
                val dryingDurationSeconds = 11
                startTimer(dryingDurationSeconds){
                    // Kurulama adımı tamamlandıktan sonra, el yıkama işlemi tamamlandı mesajını göster ve uygulamayı sonlandır
                    binding.timerTextView.text = "El yıkama tamamlandı!"
                    speak("El yıkama tamamlandı.")

                    val intent = Intent(this, CategoriesActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else -> {
                binding.timerTextView.text = "El yıkama tamamlandı!"
                speak("El yıkama tamamlandı.")
            }
        }
    }
    private fun playVideo(videoResId: Int) {
        val videoUri = Uri.parse("android.resource://${packageName}/$videoResId")
        binding.videoView.setVideoURI(videoUri)
        binding.videoView.start()

        // Video tamamlandığında tekrar baştan oynat
        binding.videoView.setOnCompletionListener {
            binding.videoView.start()
        }
    }
    private fun startTimer(seconds: Int, onFinish: () -> Unit) {
        object : CountDownTimer((seconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = (millisUntilFinished / 1000).toString()
                val secondsRemaining = millisUntilFinished / 1000

                // Her saniye başında geri sayımı sesli olarak söyler
               // speak(secondsRemaining.toString())

                // Her saniye yerine son 15 saniyede özel bir mesaj göster
                if (secondsRemaining == 15L) {
                    speak("Son 15 saniye")
                }
                // Her saniye yerine son 3 saniye geri sayımı sesli olarak söyler
                if (secondsRemaining <= 3) {
                    speak(secondsRemaining.toString())
                }
            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }
}
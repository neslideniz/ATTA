package com.example.autismapp.ui

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityEmotionMatchingBinding
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.Locale


class EmotionMatchingActivity : AppCompatActivity(),  TextToSpeech.OnInitListener  {
    private lateinit var binding: ActivityEmotionMatchingBinding
    private var textToSpeech: TextToSpeech? = null
    private var correctAnswersCount = 0
    private var allAnswersCorrect = false
    private var sadCorrect = false
    private var happyCorrect = false
    private var angryCorrect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_category)
        binding = ActivityEmotionMatchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TextToSpeech'i başlat
        textToSpeech = TextToSpeech(this, this)

        dragAndDrop()
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
                override fun onDone(utteranceId: String?) {
                    if (allAnswersCorrect) {
                        runOnUiThread {
                            val intent = Intent(applicationContext, CategoriesActivity::class.java)
                            startActivity(intent)
                            finish()  // Bu activity'yi kapat
                        }
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

    fun dragAndDrop(){

        // Sürükleme işlemi için
        val longClickListener = View.OnLongClickListener {view ->
            val item = ClipData.Item((view as TextView).text)
            val mimeType = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val dragData = ClipData(view.text, mimeType, item)

            // Drag gölgesi olustur
            val dragShadow = View.DragShadowBuilder(view)

            // Drag işlemini baslat
            view.startDragAndDrop(dragData, dragShadow, null, 0)

            true
        }

        // textView i sürükleyebiliyoruz
        binding.sadTextView.setOnLongClickListener(longClickListener)
        binding.angryTextView.setOnLongClickListener(longClickListener)
        binding.happyTextView.setOnLongClickListener(longClickListener)


        val dragListener = View.OnDragListener { view, event ->
            when(event.action){
                DragEvent.ACTION_DRAG_STARTED->{
                    event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }
                DragEvent.ACTION_DRAG_ENTERED->{
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION-> true
                DragEvent.ACTION_DRAG_EXITED->{
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DROP->{
                    val item = event.clipData.getItemAt(0)
                    val dragData = item.text.toString()
                    view.invalidate()

                    if (view is View){
                        when(view.id){
                            R.id.sadView -> {
                                if (dragData == "ÜZGÜN") {
                                    if (!sadCorrect) {
                                        speak("doğru, üzgün")
                                        sadCorrect = true
                                        correctAnswersCount++
                                    } else {
                                        speak("bu zaten doğru, üzgün")
                                    }
                                } else {
                                    speak("yanlış, üzgün değil")
                                }
                            }
                            R.id.happyView -> {
                                if (dragData == "MUTLU") {
                                    if (!happyCorrect) {
                                        speak("doğru, mutlu")
                                        happyCorrect = true
                                        correctAnswersCount++
                                    } else {
                                        speak("bu zaten doğru, mutlu")
                                    }
                                } else {
                                    speak("yanlış, mutlu değil")
                                }
                            }
                            R.id.angryView -> {
                                if (dragData == "SİNİRLİ") {
                                    if (!angryCorrect) {
                                        speak("doğru, sinirli")
                                        angryCorrect = true
                                        correctAnswersCount++
                                    } else {
                                        speak("bu zaten doğru, sinirli")
                                    }
                                } else {
                                    speak("yanlış, sinirli değil")
                                }
                            }
                        }

                        if (correctAnswersCount == 3) {
                            allAnswersCorrect = true
                            speak("Tebrikler, başarıyla tamamladınız.", "END_OF_SPEECH")
                        }

                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED->{
                    view.invalidate()
                    true
                }
                else-> false
            }
        }
        binding.sadView.setOnDragListener(dragListener)
        binding.happyView.setOnDragListener(dragListener)
        binding.angryView.setOnDragListener(dragListener)


    }


}



package com.example.autismapp.ui.maths

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autismapp.databinding.ActivityBaseMathsBinding
import java.util.Locale

abstract class BaseMathsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityBaseMathsBinding
    private var textToSpeech: TextToSpeech? = null
    protected var currentQuestionIndex = 0
    protected abstract val questions: List<QuestionsMaths>
    protected abstract val initialPrompt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseMathsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)

        binding.answerButton1.setOnClickListener { checkAnswer(0, binding.answerButton1) }
        binding.answerButton2.setOnClickListener { checkAnswer(1, binding.answerButton2) }
        binding.answerButton3.setOnClickListener { checkAnswer(2, binding.answerButton3) }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Türkçe dil desteklenmiyor", Toast.LENGTH_SHORT).show()
            } else {
                speak(initialPrompt)
                updateQuestion()
            }
        } else {
            Toast.makeText(this, "Text-to-Speech başlatılamadı", Toast.LENGTH_SHORT).show()
        }
    }

    protected fun updateQuestion() {
        val question = questions[currentQuestionIndex]
        binding.questionImageView.setImageResource(question.imageResId)
        binding.answerButton1.text = question.options[0].toString()
        binding.answerButton2.text = question.options[1].toString()
        binding.answerButton3.text = question.options[2].toString()

        val defaultColor = Color.parseColor("#FAB74A")
        binding.answerButton1.setBackgroundColor(defaultColor)
        binding.answerButton2.setBackgroundColor(defaultColor)
        binding.answerButton3.setBackgroundColor(defaultColor)
    }

    protected fun checkAnswer(optionIndex: Int, clickedButton: Button) {
        val question = questions[currentQuestionIndex]
        if (question.options[optionIndex] == question.correctAnswer) {
            speak("Tebrikler! Doğru cevap")
            clickedButton.setBackgroundColor(Color.GREEN)
        } else {
            speak("Üzgünüm, yanlış cevap.")
            clickedButton.setBackgroundColor(Color.RED)
        }

        highlightCorrectAnswer(question.correctAnswer)

        binding.answerButton1.postDelayed({
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                updateQuestion()
                speak(initialPrompt)
            } else {
                Toast.makeText(this, "Tüm soruları tamamladınız!", Toast.LENGTH_LONG).show()
                currentQuestionIndex = 0
                //updateQuestion()
                val intent = Intent(this, CategoriesMathsActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

    private fun highlightCorrectAnswer(correctAnswer: Int) {
        if (questions[currentQuestionIndex].options[0] == correctAnswer) {
            binding.answerButton1.setBackgroundColor(Color.GREEN)
        } else if (questions[currentQuestionIndex].options[1] == correctAnswer) {
            binding.answerButton2.setBackgroundColor(Color.GREEN)
        } else if (questions[currentQuestionIndex].options[2] == correctAnswer) {
            binding.answerButton3.setBackgroundColor(Color.GREEN)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech?.shutdown()
    }

    private fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}

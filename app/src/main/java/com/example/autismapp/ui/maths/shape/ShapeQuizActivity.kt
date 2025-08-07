package com.example.autismapp.ui.maths.shape

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.Toast
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityShapeQuizBinding
import com.example.autismapp.ui.maths.CategoriesMathsActivity
import java.util.Locale

class ShapeQuizActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityShapeQuizBinding
    private var textToSpeech: TextToSpeech? = null

    val questionsShape = listOf(
        ShapeQuiz(R.drawable.dikdortgenquiz, listOf("kare", "üçgen", "dikdörtgen"), "dikdörtgen"),
        ShapeQuiz(R.drawable.dairequiz, listOf("kare", "üçgen", "daire"), "daire"),
        ShapeQuiz(R.drawable.karequiz, listOf("kare", "daire", "dikdörtgen"), "kare"),
        ShapeQuiz(R.drawable.ucgenquiz, listOf("daire", "dikdörtgen", "üçgen"), "üçgen")
    )

    var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShapeQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)
        updateQuestion()

        binding.answerButton1.setOnClickListener { checkAnswer(0, binding.answerButton1) }
        binding.answerButton2.setOnClickListener { checkAnswer(1, binding.answerButton2) }
        binding.answerButton3.setOnClickListener { checkAnswer(2, binding.answerButton3) }
    }

    protected fun updateQuestion() {
        val question = questionsShape[currentQuestionIndex]
        binding.questionImageView.setImageResource(question.imageResId)
        binding.answerButton1.text = question.options[0].toString()
        binding.answerButton2.text = question.options[1].toString()
        binding.answerButton3.text = question.options[2].toString()

        val defaultColor = Color.parseColor("#FAB74A")
        binding.answerButton1.setBackgroundColor(defaultColor)
        binding.answerButton2.setBackgroundColor(defaultColor)
        binding.answerButton3.setBackgroundColor(defaultColor)

        speak("Bu hangi şekil")
    }

    protected fun checkAnswer(optionIndex: Int, clickedButton: Button) {
        val question = questionsShape[currentQuestionIndex]
        if (question.options[optionIndex] == question.correctAnswer) {
            speak("Tebrikler! Doğru cevap")
            clickedButton.setBackgroundColor(Color.GREEN)
        } else {
            speak("Üzgünüm, yanlış cevap.")
            clickedButton.setBackgroundColor(Color.RED)
            highlightCorrectAnswer(question.correctAnswer)
        }

        binding.answerButton1.postDelayed({
            currentQuestionIndex++
            if (currentQuestionIndex < questionsShape.size) {
                updateQuestion()
            } else {
                Toast.makeText(this, "Tüm soruları tamamladınız!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, CategoriesShapesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

    private fun highlightCorrectAnswer(correctAnswer: String) {
        val question = questionsShape[currentQuestionIndex]
        if (question.options[0] == correctAnswer) {
            binding.answerButton1.setBackgroundColor(Color.GREEN)
        } else if (question.options[1] == correctAnswer) {
            binding.answerButton2.setBackgroundColor(Color.GREEN)
        } else if (question.options[2] == correctAnswer) {
            binding.answerButton3.setBackgroundColor(Color.GREEN)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Türkçe dil desteklenmiyor", Toast.LENGTH_SHORT).show()
            } else {
                updateQuestion()
            }
        } else {
            Toast.makeText(this, "Text-to-Speech başlatılamadı", Toast.LENGTH_SHORT).show()
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

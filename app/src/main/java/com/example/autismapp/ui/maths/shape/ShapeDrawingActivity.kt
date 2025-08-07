package com.example.autismapp.ui.maths.shape

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.autismapp.databinding.ActivityShapeDrawingBinding
import com.example.autismapp.ui.maths.CategoriesMathsActivity
import java.util.Locale

class ShapeDrawingActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityShapeDrawingBinding
    private var textToSpeech: TextToSpeech? = null
    private var currentColor: Int = Color.BLACK

    private val shapes = listOf("kare", "üçgen", "dikdörtgen", "daire")
    private var currentShapeIndex = 0

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShapeDrawingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)

        updateShapeInstruction()

        binding.completeDrawingButton.setOnClickListener {
            checkDrawing()
        }

        binding.clearButton.setOnClickListener {
            binding.drawingView.clear()
        }

        binding.nextShapeButton.setOnClickListener {
            nextShape()
        }

        binding.chooseColorButton.setOnClickListener {
            val color = Color.RED
                binding.drawingView.setDrawingColor(color)
        }
    }

    private fun updateShapeInstruction() {
        val currentShape = shapes[currentShapeIndex]
        binding.shapeInstruction.text = "Lütfen bir $currentShape çizin."
        speak("Lütfen bir $currentShape çizin.")
    }

    private fun checkDrawing() {
        val currentShape = shapes[currentShapeIndex]
        val isCorrect = binding.drawingView.isDrawingCorrect(currentShape)
        if (isCorrect) {
            speak("Tebrikler! Doğru çizdiniz. Şimdi tekrar çizebilir veya bir sonraki şekle geçebilirsiniz.")
        } else {
            speak("Üzgünüm, yanlış çizdiniz, tekrar aynı şekli deneyebilir veya yeni şekle geçebilirsiniz.")
        }
    }

    private fun nextShape() {
        currentShapeIndex++
        if (currentShapeIndex < shapes.size) {
            updateShapeInstruction()
            binding.drawingView.clear()
        } else {
            speak("Tüm şekilleri başarıyla çizdiniz! Tebrikler!")
            Toast.makeText(this, "Tüm şekilleri başarıyla çizdiniz! Tebrikler!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, CategoriesMathsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showColorPickerDialog() {
        // Renk seçici dialog gösterimi burada yapılabilir, kullanıcının seçtiği renk currentColor değişkenine atanabilir.
        // Basitçe varsayılan olarak siyah rengi seçelim.
        currentColor = Color.BLACK  // Varsayılan olarak siyah rengi ayarla
        binding.drawingView.setDrawingColor(currentColor)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("tr", "TR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Türkçe dil desteklenmiyor", Toast.LENGTH_SHORT).show()
            } else {
                updateShapeInstruction()
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

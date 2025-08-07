package com.example.autismapp.ui.maths.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityShapeRecognitionBinding
import java.util.Locale

class ShapeRecognitionActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityShapeRecognitionBinding
    private lateinit var textToSpeech: TextToSpeech
    private var currentShapeIndex = 0
    private val shapes = listOf(
        Shape(
            R.drawable.ucgen,
            "Üçgen: \n 3 kenarı ve 3 köşesi vardır."
        ),
        Shape(
            R.drawable.kare,
            "Kare: \n 4 kenarı ve 4 köşesi vardır."
        ),
        Shape(
            R.drawable.daire,
            "Daire: \n Köşesi yoktur, tek bir sürekli çizgiden oluşur."
        ),
        Shape(
            R.drawable.dikdortgen,
            "Dikdörtgen: \n4 köşesi ve 2 kısa 2 uzun kenarı vardır."
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_shape_recognition)
        binding = ActivityShapeRecognitionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)

        binding.listenButton.setOnClickListener { describeShape() }
        binding.nextShapeButton.setOnClickListener { showNextShape() }
        binding.backShapeButton.setOnClickListener { showBackShape() }
        binding.nextActivityButton.setOnClickListener { navigateToNextActivity() }
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, CategoriesShapesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun describeShape() {
        val description = shapes[currentShapeIndex].description
        textToSpeech.speak(description, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun showNextShape() {
        currentShapeIndex = (currentShapeIndex + 1) % shapes.size
        updateShape()
    }
    private fun showBackShape(){
        currentShapeIndex = if (currentShapeIndex - 1 < 0) shapes.size - 1 else currentShapeIndex - 1
        updateShape()
    }

    private fun updateShape() {
        val shape = shapes[currentShapeIndex]
        binding.shapeImageView.setImageResource(shape.imageResId)
        binding.descriptionTextView.text = shape.description
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("tr", "TR")
        }

    }
    override fun onDestroy() {
        textToSpeech.shutdown()
        super.onDestroy()
    }
}
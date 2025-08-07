package com.example.autismapp.ui.maths.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityCategoriesShapesBinding

class CategoriesShapesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesShapesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_categories_shapes)
        binding = ActivityCategoriesShapesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.learnShapesButton.setOnClickListener {
            val intent = Intent(this, LearnShapesActivity::class.java)
            startActivity(intent)
        }
        binding.shapeQuizButton.setOnClickListener {
            val intent = Intent(this, ShapeQuizActivity::class.java)
            startActivity(intent)
        }
        binding.shapeRecognitionButton.setOnClickListener {
            val intent = Intent(this, ShapeRecognitionActivity::class.java)
            startActivity(intent)
        }
        binding.shapeDrawingButton.setOnClickListener {
            val intent = Intent(this, ShapeDrawingActivity::class.java)
            startActivity(intent)
        }

    }


}
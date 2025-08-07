package com.example.autismapp.ui.maths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.databinding.ActivityCategoriesMathsBinding
import com.example.autismapp.ui.maths.shape.CategoriesShapesActivity
import com.example.autismapp.ui.maths.shape.LearnShapesActivity

class CategoriesMathsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoriesMathsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_categories_maths)
        binding = ActivityCategoriesMathsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.learnNumbersButton.setOnClickListener {
            val intent = Intent(this, LearnNumbersActivity::class.java)
            startActivity(intent)
        }
        binding.numberCountingButton.setOnClickListener {
            val intent = Intent(this, NumberRecognitionActivity::class.java)
            startActivity(intent)
        }
        binding.additionSubtractionButton.setOnClickListener {
            val intent = Intent(this, AdditionSubtractionActivity::class.java)
            startActivity(intent)
        }
        binding.shapeRecognitionButton.setOnClickListener {
            val intent = Intent(this, CategoriesShapesActivity::class.java)
            startActivity(intent)
        }
    }
}
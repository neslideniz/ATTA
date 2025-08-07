package com.example.autismapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.autismapp.databinding.ActivityFirstScreenBinding
import android.animation.Animator
import android.os.Handler
import com.example.autismapp.ui.maths.shape.CategoriesShapesActivity

class FirstScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_first_screen)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation()
    }
    fun animation(){
        val animationView = binding.animationView
        animationView.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
                // Animasyon başladığında yapılacak işlemler
                Handler().postDelayed({
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },1400)
            }
            override fun onAnimationEnd(animation: Animator) {
                // animasyon bitince yapılacaklar
            }
            override fun onAnimationCancel(animation: Animator) {
                // Animasyon iptal edildiğinde yapılacak işlemler
            }
            override fun onAnimationRepeat(animation: Animator) {
                // Animasyon tekrarlandığında yapılacak işlemler
            }
        })
        animationView.playAnimation()
    }
}
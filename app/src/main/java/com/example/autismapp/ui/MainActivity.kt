package com.example.autismapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.autismapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // firebase değişkenleri atadık
    private lateinit var auth: FirebaseAuth
    private var storage = Firebase.storage
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var database : FirebaseFirestore
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { logIn() }
        binding.signUpButton.setOnClickListener { signUp() }
        binding.signUpTextView.setOnClickListener { visibility() }

        storage = FirebaseStorage.getInstance()
        firebaseAnalytics = Firebase.analytics
        auth = FirebaseAuth.getInstance() // Initialize Firebase Auth
        database = FirebaseFirestore.getInstance()
        // eger kullanıcı daha önce giriş yaptıysa,uygulamaya girişte MainActivity ekranı gelmeden FirstActivity ekranına gececek
       /* val currentUser = auth.currentUser
        if (currentUser != null){
            nextActivity()
        }*/
    }
    fun signUp(){
        email = binding.emailEditText.text.toString()
        password = binding.passwordEditText.text.toString()
        var username = binding.usernameEditText.text.toString()
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill in the blank fields", Toast.LENGTH_SHORT).show()
            return // Gerekli alanlar dolu değilse işlemi sonlandır
        }
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                var currentUser = auth.currentUser
                var profileUpdates = userProfileChangeRequest {
                    displayName = username
                }
                if (currentUser != null) {
                    currentUser.updateProfile(profileUpdates).addOnCompleteListener {
                        if (it.isSuccessful){
                           //Toast.makeText(applicationContext, "username added",Toast.LENGTH_LONG).show()
                            Log.d("TAG", "User profile updated.")
                        }
                    }
                    Log.d("TAG","createUserWithEmail:success")
                    nextActivity()
                }
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
    fun logIn(){
        email = binding.emailEditText.text.toString()
        password = binding.passwordEditText.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill in the blank fields", Toast.LENGTH_SHORT).show()
            return // Gerekli alanlar dolu değilse işlemi sonlandır
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("TAG", "signInWithEmail: success")
                var currentUser = auth.currentUser?.displayName.toString()
               // Toast.makeText(applicationContext, "Welcome: $currentUser ", Toast.LENGTH_SHORT).show()
                nextActivity()
            }
        }.addOnFailureListener {
            Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
    fun visibility(){
        binding.signUpButton.visibility = View.VISIBLE
        binding.usernameEditText.visibility = View.VISIBLE
        binding.loginButton.visibility = View.GONE
        binding.signUpTextView.visibility = View.GONE
    }
    private fun nextActivity() {
        val intent = Intent(this, ParentActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.example.autismapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.autismapp.ChildsInformation
import com.example.autismapp.R
import com.example.autismapp.databinding.ActivityParentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ParentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParentBinding

    // firebase değişkenleri atadık
    private lateinit var auth: FirebaseAuth


    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    lateinit var shareList : ArrayList<ChildsInformation>
    private var postHashMap = hashMapOf<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_parent)
        binding = ActivityParentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // Initialize Firebase Auth
        firestore = FirebaseFirestore.getInstance() // Firestore instance'ı al
        storage = FirebaseStorage.getInstance() // Firebase Storage instance'ı al

        operationsSpinner()
        binding.saveButton.setOnClickListener {
            save(it)
        }
    }
    fun operationsSpinner(){

        // gender spinner
        val genderAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_gender, android.R.layout.simple_spinner_item)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.childsGenderSpinner.adapter = genderAdapter

        //p0: Seçimin yapıldığı AdapterView (bu durumda Spinner).
        //p1: Seçilen öğenin görünümü (genellikle kullanılmaz).
        //p2: Seçilen öğenin pozisyonu (index).
        //p3: Seçilen öğenin row id'si (genellikle kullanılmaz).
        binding.childsGenderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Öğenin seçildiği durum için gerekli işlemler burada yapılabilir.
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please Select an Option", Toast.LENGTH_SHORT).show()
            }
        }

        // autism level spinner
        val autismLevelAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_autism_level, android.R.layout.simple_spinner_item)
        autismLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.childsAutismLevelSpinner.adapter = autismLevelAdapter

        binding.childsAutismLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Öğenin seçildiği durum için gerekli işlemler burada yapılabilir.
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please Select an Option", Toast.LENGTH_SHORT).show()
            }
        }

        // screen time spinner
        val screenTimeAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_screen_time, android.R.layout.simple_spinner_item)
        screenTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.childsScreenTimeSpinner.adapter = screenTimeAdapter

        binding.childsScreenTimeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Öğenin seçildiği durum için gerekli işlemler burada yapılabilir.
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Please Select an Option", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun save(view: View){
        var childsUsername = binding.childsUsernameEditText.text.toString()
        var childsAge = binding.childsAgeEditText.text.toString()
        var childsGenderIndex = binding.childsGenderSpinner.selectedItem.toString()
        var childsAutismLevel = binding.childsAutismLevelSpinner.selectedItem.toString()
        var screenTime = binding.childsScreenTimeSpinner.selectedItem.toString()

        if (childsUsername.isEmpty() || childsAge.isEmpty() || childsGenderIndex.isEmpty() || childsAutismLevel.isEmpty() || screenTime.isEmpty()){
            Toast.makeText(applicationContext,"Please fill out all fields", Toast.LENGTH_SHORT ).show()
        }else{
            postHashMap.put("childsUsername", childsUsername)
            postHashMap.put("childsAge", childsAge)
            postHashMap.put("childsGenderIndex", childsGenderIndex)
            postHashMap.put("childsAutismLevel", childsAutismLevel)
            postHashMap.put("screenTime", screenTime)

            firestore.collection("Child").add(postHashMap).addOnCompleteListener {
                if (it.isSuccessful) {
                    //Toast.makeText(applicationContext, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    // veriler basarılı bır sekılde fırabase e kaydedıldı

                    if (childsAutismLevel.equals("modarate", ignoreCase = true)){ // buraya daha sonradan dıger yas veya farklı degerler ıcınde şart koyabılırsın
                        val intent = Intent(this, CategoriesActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        // modarate secılmezse yapılacakları ekle
                        val intent = Intent(this, CategoriesActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Toast.makeText(applicationContext, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }


}
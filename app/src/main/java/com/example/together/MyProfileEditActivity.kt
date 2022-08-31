package com.example.together

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyProfileEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_edit)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userId = myUserProfile.userID
        val db = Firebase.firestore

        val etName = findViewById<EditText>(R.id.name)
        etName.setText(myUserProfile.name)
        val etDescription = findViewById<EditText>(R.id.description)
        etDescription.setText(myUserProfile.description)
        val etGender= findViewById<EditText>(R.id.gender)
        etGender.setText(myUserProfile.gender)
        val etAge= findViewById<EditText>(R.id.age)
        etAge.setText(myUserProfile.age)
        val etCity= findViewById<EditText>(R.id.city)
        etCity.setText(myUserProfile.city)

        val btnEdit = findViewById<Button>(R.id.buttonSave)
        btnEdit.setOnClickListener{
            val user = hashMapOf(
                "name" to myUserProfile.name,
                "surname" to myUserProfile.surname,
                "email" to myUserProfile.email,
                "age" to myUserProfile.age,
                "gender" to myUserProfile.gender,
                "city" to etCity.text.toString(),
            "description" to myUserProfile.description)

            db.collection("user").document(userId).set(user)
            finish()
        }
    }
}
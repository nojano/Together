package com.example.together

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton:Button = findViewById(R.id.loginbtn)
        loginButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPostActivity::class.java)
            startActivity(intent)
        }

        val signupButton:Button = findViewById(R.id.signupbtn)
        signupButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
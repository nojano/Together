package com.example.together

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class HomepageActivity : AppCompatActivity() {
    private var layoutmanager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val tvUserId: TextView = findViewById(R.id.user_id)
        val tvUserEmail: TextView = findViewById(R.id.user_email)
        val btnLogout: Button = findViewById(R.id.button_logout)

        //TEST for sign-up and login
        val userId = intent.getStringExtra("user_id")
        val userEmail = intent.getStringExtra("email")

        tvUserId.text = "User ID :: $userId"
        tvUserEmail.text = "email :: $userEmail"

        btnLogout.setOnClickListener{
            //Logout from app
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this@HomepageActivity,
                "You are now logged out",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this@HomepageActivity, MainActivity::class.java))
            finish()
        }

        val recyclerViewHomepage = findViewById<RecyclerView>(R.id.recyclerViewHomepage)

        layoutmanager = LinearLayoutManager(this)
        recyclerViewHomepage.layoutManager = layoutmanager

        adapter = RecyclerAdapterHomepage()
        recyclerViewHomepage.adapter = adapter

    }
}
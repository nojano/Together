package com.example.together

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomepageActivity : AppCompatActivity() {
    private var layoutmanager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(findViewById(R.id.toolbar))

        val tvUserId: TextView = findViewById(R.id.user_id)
        val tvUserEmail: TextView = findViewById(R.id.user_email)
        val btnLogout: Button = findViewById(R.id.button_logout)

        //TEST for sign-up and login
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
        tvUserId.text = "User ID :: $userId"
        tvUserEmail.text = "email :: $userEmail"

        btnLogout.setOnClickListener {
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

        //Test per la Get dal database
        val db = Firebase.firestore
        val tag = "Test_get_database"
        //Ritorna il documento associato all'uente loggato
        val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(
                        tag,
                        "DocumentSnapshot data: ${document.data}"
                    ) //ritorna tutto il documento
                    Log.d(
                        tag,
                        "DocumentSnapshot data: ${document.data?.getValue("username")}"
                    ) //ritorna solo l'username
                } else {
                    Log.d(tag, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(tag, "get failed with ", exception)
            }

    }

    //Handle actions of toolbar items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this@HomepageActivity, SettingsActivity::class.java))
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    //Display the items of the toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.homepage_toolbar, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        return super.onCreateOptionsMenu(menu)
    }
}
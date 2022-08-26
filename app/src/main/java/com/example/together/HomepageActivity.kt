package com.example.together

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomepageActivity : AppCompatActivity() {
    private var layoutmanager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        setSupportActionBar(findViewById(R.id.toolbar))


        val recyclerViewHomepage = findViewById<RecyclerView>(R.id.recyclerViewHomepage)

        layoutmanager = LinearLayoutManager(this)
        recyclerViewHomepage.layoutManager = layoutmanager

        adapter = RecyclerAdapterHomepage()
        recyclerViewHomepage.adapter = adapter


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
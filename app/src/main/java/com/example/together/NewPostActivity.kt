package com.example.together

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Set the switch
        val switch: Switch = findViewById(R.id.newPostGroupSwitch)
        startSwitchListener(
            switch,
            findViewById(R.id.newPostNeededMembers),
            findViewById(R.id.newPostMembersAlreadyIn)
        )

        //set the spinner
        val categorySpinner: Spinner = findViewById(R.id.newPostSpinnerCategory)
        setCategoryOnSpinner(categorySpinner, this)


        val db = Firebase.firestore

        val submitButton: Button = findViewById(R.id.newPostPublishButton)
        submitButton.setOnClickListener {
            if (checkPostForm(this@NewPostActivity)) {
                val post = fillPost(this@NewPostActivity)
                if (publishPost(db, post)) {
                    Toast.makeText(
                        this@NewPostActivity,
                        "Post Published",
                        Toast.LENGTH_SHORT
                    ).show()
                    getPost()
                    val intent = Intent(
                        this@NewPostActivity,
                        MainActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(
                        this@NewPostActivity,
                        "Unable to publish post",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}


fun checkPostForm(postActivity: NewPostActivity): Boolean {
    when {
        TextUtils.isEmpty(
            postActivity.findViewById<EditText>(R.id.newPostTitle).text.toString()
        ) -> {
            Toast.makeText(
                postActivity,
                "Add Title",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        TextUtils.isEmpty(
            postActivity.findViewById<EditText>(R.id.newPostDescription).text.toString()
        ) -> {
            Toast.makeText(
                postActivity,
                "Enter a short description",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        TextUtils.isEmpty(
            postActivity.findViewById<EditText>(R.id.newPostCity).text.toString()
        ) -> {
            Toast.makeText(
                postActivity,
                "Enter the City ",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        postActivity.findViewById<Switch>(R.id.newPostGroupSwitch).isChecked -> {
            if (postActivity.findViewById<EditText>(R.id.newPostMembersAlreadyIn).text.isEmpty() && postActivity.findViewById<EditText>(
                    R.id.newPostNeededMembers
                ).text.isEmpty()
            ) {
                Toast.makeText(
                    postActivity,
                    "You must fill out one member field at least",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
    }
    return true
}

fun setCategoryOnSpinner(spinner: Spinner, activity: NewPostActivity) {
    ArrayAdapter.createFromResource(
        activity,
        R.array.Category,
        android.R.layout.simple_spinner_item
    ).also { adapter ->
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
    }
}

fun startSwitchListener(switch: Switch, edOne: EditText, edTwo: EditText) {
    //TODO: disable highlighting when the switch is off (try to use a ViewGroup)
    switch.setOnCheckedChangeListener { _, _ ->
        if (switch.isChecked) {
            edOne.isEnabled = true
            edTwo.isEnabled = true
        } else {
            edOne.isEnabled = false
            edTwo.isEnabled = false
        }
    }
    switch.isChecked = true
    switch.isChecked = false
}


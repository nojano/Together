package com.example.together

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        //Set the switch
        val switch: Switch = findViewById(R.id.switch1)
        startSwitchListener(
            switch,
            findViewById(R.id.textField_edit_text),
            findViewById(R.id.textField_edit_text_second)
        )

        //set the spinner
        val categorySpinner: Spinner = findViewById(R.id.spinner)
        setCategoryOnSpinner(categorySpinner, this)
    }
}


fun setCategoryOnSpinner(spinner: Spinner, activity: NewPostActivity) {
    //TODO: get Category from DB
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
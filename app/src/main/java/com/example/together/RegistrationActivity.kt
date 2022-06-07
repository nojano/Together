package com.example.together

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val signupButton: Button = findViewById(R.id.signupbtndone)
        signupButton.setOnClickListener {

            //Validate input from user
            if (passwordCheck(
                    this@RegistrationActivity,
                    findViewById(R.id.password),
                    findViewById(R.id.repeatpassword)
                )
            ) {

                //prepare the user instance
                val user = getUserAttribute(this@RegistrationActivity)

                val TAG = "RegistrationActivity"
                val db = Firebase.firestore
                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
                /*
                //Registration Successful, redirect to HomepageActivity
                val intent = Intent(
                    this@RegistrationActivity,
                    HomepageActivity::class.java
                )  //switch to another activity
                startActivity(intent)
                */
            }
        }
    }
}

fun passwordCheck(
    activity: RegistrationActivity,
    password: EditText,
    repeatPsw: EditText
): Boolean {
    if (password.text.toString() != repeatPsw.text.toString()) {
        Toast.makeText(activity, "The passwords are not equals", Toast.LENGTH_SHORT).show()
        return false
    }
    if (password.text.toString().length < 8) {
        Toast.makeText(activity, "Password must be at least 8 characters", Toast.LENGTH_SHORT)
            .show()
        return false
    }
    return true
}

fun getUserAttribute(registrationActivity: RegistrationActivity): HashMap<String, String> {
    return hashMapOf(
        "nameAndSurname" to (registrationActivity.findViewById(R.id.namesurname) as EditText).text.toString(),
        "username" to (registrationActivity.findViewById(R.id.username) as EditText).text.toString(),
        "email" to (registrationActivity.findViewById(R.id.email) as EditText).text.toString(),
        "password" to (registrationActivity.findViewById(R.id.password) as EditText).text.toString(),
        "city" to (registrationActivity.findViewById(R.id.city) as EditText).text.toString(),
        "phoneNumber" to (registrationActivity.findViewById(R.id.telephonenumber) as EditText).text.toString()
    )
}
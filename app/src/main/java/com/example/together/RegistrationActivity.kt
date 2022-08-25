package com.example.together

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val tag = "RegistrationActivity"

        val signupButton: Button = findViewById(R.id.signupbtndone)
        signupButton.setOnClickListener {

            when {
                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.email).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.password).text.toString()
                        .trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !passwordsAreEquals(this@RegistrationActivity) -> {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Passwords are not equals",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {

                    val email: String =
                        findViewById<EditText>(R.id.email).text.toString().trim { it <= ' ' }
                    val password: String =
                        findViewById<EditText>(R.id.password).text.toString().trim { it <= ' ' }

                    //Sign-up user with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this)
                        { task ->
                            //If the registration is successfully done
                            if (task.isSuccessful) {

                                //firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this@RegistrationActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d(tag, "createUserWithEmail:success")

                                //Now we insert the user created in the Firestore Database
                                //prepare the user instance
                                val user = getUserAttribute(this@RegistrationActivity)

                                val db = Firebase.firestore

                                // Add a new document with the same ID of the firebase user ID
                                db.collection("users").document(firebaseUser.uid).set(user)
                                    .addOnSuccessListener {
                                        Log.d(tag, "DocumentSnapshot successfully written!")
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(tag, "Error adding document", e)
                                    }

                                //User is registered and so logged in, we send him to the homepage
                                val intent = Intent(
                                    this@RegistrationActivity,
                                    HomepageActivity::class.java
                                )
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                //If the registration was not successful, then show the error message
                                Toast.makeText(
                                    this@RegistrationActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w(tag, "createUserWithEmail:failure", task.exception)
                            }
                        }
                }
            }
        }
    }
}

fun getUserAttribute(registrationActivity: RegistrationActivity): HashMap<String, String> {
    return hashMapOf(
        "nameAndSurname" to (registrationActivity.findViewById(R.id.namesurname) as EditText).text.toString(),
        "username" to (registrationActivity.findViewById(R.id.username) as EditText).text.toString(),
        "email" to (registrationActivity.findViewById(R.id.email) as EditText).text.toString(),
        "city" to (registrationActivity.findViewById(R.id.city) as EditText).text.toString(),
        "phoneNumber" to (registrationActivity.findViewById(R.id.telephonenumber) as EditText).text.toString()
    )
}

fun passwordsAreEquals (registrationActivity: RegistrationActivity): Boolean{
    return registrationActivity.findViewById<EditText>(R.id.password).text.toString() == registrationActivity.findViewById<EditText>(R.id.repeatpassword).text.toString()
}
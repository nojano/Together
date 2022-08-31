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

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val tag = "RegistrationActivity"

        val signupButton: Button = findViewById(R.id.signupbtndone)
        signupButton.setOnClickListener {
            //Update the post in the homepage
            getPost()
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

                                //fill the myUserProfile global class
                                setMyProfileUser(this@RegistrationActivity)

                                //register the user on Firebase Authenticator and on the Firestore Database
                                registerUser(task, getHashedUser())

                                Toast.makeText(
                                    this@RegistrationActivity,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d(tag, "createUserWithEmail:success")

                                //User is registered and so logged in, we send him to the homepage
                                val intent = Intent(
                                    this@RegistrationActivity,
                                    MainActivity::class.java
                                )
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                            else {
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

        startEnterKeyListener(findViewById(R.id.description), signupButton)

    }
}

fun passwordsAreEquals (registrationActivity: RegistrationActivity): Boolean{
    return registrationActivity.findViewById<EditText>(R.id.password).text.toString() == registrationActivity.findViewById<EditText>(R.id.repeatpassword).text.toString()
}

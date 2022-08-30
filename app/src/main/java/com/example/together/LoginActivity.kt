package com.example.together

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.together.databinding.FragmentHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //used for enter key listener
        val passwordEditText = findViewById<EditText>(R.id.password)

        val loginButton:Button = findViewById(R.id.loginbtn)
        loginButton.setOnClickListener {
            getPost()
            when {
                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.username).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.password).text.toString()
                        .trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {


                    val email: String =
                        findViewById<EditText>(R.id.username).text.toString().trim { it <= ' ' }
                    val password: String =
                        passwordEditText.text.toString().trim { it <= ' ' }
                    //Login user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{ task ->
                                //If the login is successfully done
                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        this@LoginActivity,
                                        "You are logged in successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    //User is logged in, we send him to the homepage
                                    val intent = Intent(
                                        this@LoginActivity,
                                        MainActivity::class.java
                                    )
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                } else {
                                    //If the login was not successful, then show the error message
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                }
            }
        }

        val signupButton:Button = findViewById(R.id.signupbtn)
        signupButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        val forgotPswTv: TextView = findViewById(R.id.forgotpass)
        forgotPswTv.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //start te Listener for the EnterKey on the password field
        startEnterKeyListener(findViewById(R.id.password), loginButton)
    }
}


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

const val TAG = "ChangePasswordActivity"

class ChangePassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = FirebaseAuth.getInstance().currentUser
        val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

        val updatePasswordButton: Button = findViewById(R.id.updatePasswordButton)
        updatePasswordButton.setOnClickListener {
            when {
                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.currentPassword).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter current password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.newPassword).text.toString().trim{ it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please enter new password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(
                    findViewById<EditText>(R.id.repeatPassword).text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this,
                        "Please repeat the new password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !newPasswordsAreEquals(this@ChangePassword) -> {
                    Toast.makeText(
                        this@ChangePassword,
                        "Passwords are not equals",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                else -> {
                    val userCurrentPassword = findViewById<EditText>(R.id.currentPassword).text.toString().trim { it <= ' ' }
                    val userNewPassword = findViewById<EditText>(R.id.newPassword).text.toString().trim{ it <= ' ' }

                    //We verify the current password performing the Login.
                    // If login is successful, password is correct and we can proceed with the update
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userCurrentPassword)
                        .addOnCompleteListener{ task ->
                            //If the login is successfully done
                            if (task.isSuccessful) {
                                Log.d(TAG, "User current password is correct.")

                                user!!.updatePassword(userNewPassword)
                                    .addOnCompleteListener { task1 ->
                                        if (task1.isSuccessful) {
                                            Log.d(TAG, "User password updated.")
                                            Toast.makeText(
                                                this,
                                                "Your password has been updated",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            //Password has been updated successfully, we send the user on the settings activity
                                            val intent = Intent(
                                                this,
                                                SettingsActivity::class.java
                                            )

                                            startActivity(intent)
                                            finish()
                                        }
                                        else{
                                            Toast.makeText(
                                                this,
                                                task1.exception!!.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            /*Toast.makeText(
                                                this,
                                                "Password must be at least 6 characters long",
                                                Toast.LENGTH_SHORT
                                            ).show()*/
                                            Log.d(TAG, "Update password failure.")
                                        }
                                    }

                            } else {
                                //If the login was not successful, then show the error message
                                Toast.makeText(
                                    this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                Toast.makeText(
                                    this,
                                    "Please enter correct password currently used",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                }
            }
        }
    }
}

fun newPasswordsAreEquals(changePasswordActivity: ChangePassword): Boolean {
    return changePasswordActivity.findViewById<EditText>(R.id.newPassword).text.toString() == changePasswordActivity.findViewById<EditText>(
        R.id.repeatPassword).text.toString()
}





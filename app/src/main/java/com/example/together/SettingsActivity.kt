package com.example.together

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.toolbar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, MySettingsFragment())
            .commit()
    }
}

class MySettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        //Display user email
        val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
        val emailPreference: Preference? = findPreference("email")
        emailPreference?.summary = userEmail

        //Handle change password
        val passwordPreference: Preference? = findPreference("password")
        passwordPreference?.setOnPreferenceClickListener {
            startActivity(Intent(activity, ChangePassword::class.java))
            true
        }

        //Handle logout
        val logoutPreference: Preference? = findPreference("logout")
        logoutPreference?.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this.requireActivity(),
                "You are now logged out",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
            true
        }



    }
}
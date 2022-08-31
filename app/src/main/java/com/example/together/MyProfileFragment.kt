package com.example.together

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.together.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "MyProfileFragment"

class MyProfileFragment : Fragment() {
    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "onCreate Called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView Called")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated Called")
        //Setup the toolbar
        binding.toolbar.inflateMenu(R.menu.homepage_toolbar)

        //Disabling search item
        val searchItem = binding.toolbar.menu.findItem(R.id.search)
        searchItem.isVisible = false
        //Disabling add post item
        val addItem = binding.toolbar.menu.findItem(R.id.add)
        addItem.isVisible = false
        //Disabling settings menu
        val settingsItem = binding.toolbar.menu.findItem(R.id.action_settings)
        settingsItem.isVisible = false

        //Handle actions of toolbar items
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                else -> false
            }
        }

        binding.buttonEdit.setOnClickListener{
            val intent = Intent(
                activity,
                MyProfileEditActivity::class.java
            )
            startActivity(intent)
        }

        //getUserInfo()
        val tvUserName = binding.name
        tvUserName.text = myUserProfile.name
        val tvUserDescription = binding.description
        tvUserDescription.text = myUserProfile.description
        val tvUserGender = binding.gender
        tvUserGender.text = "Gender: " + myUserProfile.gender
        val tvUserAge = binding.age
        tvUserAge.text = "Age: " + myUserProfile.age
        val tvUserCity = binding.city
        tvUserCity.text = "City: " + myUserProfile.city


    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView Called")
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
        //appgetUserInfo()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy Called")
        super.onDestroy()
    }

    private fun getUserInfo(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db = Firebase.firestore
        val docRef = db.collection("user").document(userId)
        docRef.get().addOnSuccessListener { document ->
            if(document != null){
                binding.city.text = "City: " + document.data?.getValue("city").toString()
            }
        }
    }


}
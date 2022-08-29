package com.example.together

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.together.databinding.FragmentHomePageBinding
import com.example.together.databinding.FragmentJoinedGroupsBinding
import com.example.together.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Joined_Groups : Fragment() {


    private var _binding: FragmentJoinedGroupsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinedGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Setup the toolbar
        binding.toolbar.inflateMenu(com.example.together.R.menu.homepage_toolbar)

        //Disabling search item
        val searchItem = binding.toolbar.menu.findItem(com.example.together.R.id.search)
        searchItem.isVisible = false
        //Disabling add post item
        val addItem = binding.toolbar.menu.findItem(com.example.together.R.id.add)
        addItem.isVisible = false

        //Handle actions of toolbar items
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                com.example.together.R.id.action_settings -> {
                    startActivity(Intent(activity, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val tvtitle = binding.title
        val tvcity = binding.city
        val tvcategory = binding.category
        val tvowneruser = binding.owneruser
        val tvdescription = binding.description
        val tvmembersAlreadyIn = binding.cardnumberavailable
        val tvNeededMembers = binding.cardmaxplaces

        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        val db = Firebase.firestore
        val tag = "Test_get_database"

        val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    tvtitle.text = document.data?.getValue("title").toString()
                    tvcity.text = document.data?.getValue("city").toString()
                    tvcategory.text = document.data?.getValue("category").toString()
                    tvowneruser.text = document.data?.getValue("owneruser").toString()
                    tvdescription.text = document.data?.getValue("description").toString()
                    tvmembersAlreadyIn.text = document.data?.getValue("MembersAlreadyIn").toString()
                    tvNeededMembers.text = document.data?.getValue("NeededMembers").toString()
                } else {
                    Log.d(tag, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(tag, "get failed with ", exception)
            }

         fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }
}
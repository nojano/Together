package com.example.together

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.together.databinding.FragmentCreatedGroupsBinding
import com.example.together.databinding.FragmentJoinedGroupsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Created_Groups : Fragment() {


    private var _binding: FragmentCreatedGroupsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatedGroupsBinding.inflate(inflater, container, false)
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



        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }
}


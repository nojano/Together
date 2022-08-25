package com.example.together

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.together.databinding.FragmentMyProfileBinding
import com.google.firebase.auth.FirebaseAuth


class MyProfileFragment : Fragment() {
    private var _binding: FragmentMyProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //toDelete
        val tvUserNameSurname = binding.namesurname
        val userNameSurname = FirebaseAuth.getInstance().currentUser?.email.toString()
        tvUserNameSurname.text = userNameSurname

        val btnLogout = binding.logoutbtn
        btnLogout.setOnClickListener {
            //Logout from app
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                this.requireActivity(),
                "You are now logged out",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
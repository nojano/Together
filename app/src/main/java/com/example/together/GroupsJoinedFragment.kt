package com.example.together

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.together.databinding.FragmentGroupsJoinedBinding



class GroupsJoinedFragment : Fragment() {

    private var _binding: FragmentGroupsJoinedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsJoinedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Setup the toolbar
        binding.toolbar.inflateMenu(R.menu.homepage_toolbar)

        //Search item
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


        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }

}
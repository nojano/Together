package com.example.together


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.together.databinding.FragmentGroupsCreatedBinding


class GroupsCreatedFragment : Fragment() {


    private var _binding: FragmentGroupsCreatedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsCreatedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Setup the toolbar
        binding.toolbar.inflateMenu(R.menu.homepage_toolbar)

        //Search item
        val searchItem = binding.toolbar.menu.findItem(R.id.search)
        searchItem.isVisible = true
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

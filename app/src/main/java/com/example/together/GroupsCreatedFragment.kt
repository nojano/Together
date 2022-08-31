package com.example.together

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.together.databinding.FragmentGroupsCreatedBinding
import com.example.together.databinding.FragmentHomePageBinding


class GroupsCreatedFragment : Fragment() {

    private var _binding: FragmentGroupsCreatedBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewCreatedGroups: RecyclerView

    private var layoutmanager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecycleAdapterCreatedPosts.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsCreatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewCreatedGroups = binding.recyclerViewPostCreated

        layoutmanager = LinearLayoutManager(this.context)
        recyclerViewCreatedGroups.layoutManager = layoutmanager

        adapter = RecycleAdapterCreatedPosts()
        recyclerViewCreatedGroups.adapter = adapter

        //Setup the toolbar
        binding.toolbar.inflateMenu(R.menu.homepage_toolbar)

        //Disabling settings menu
        //val settingsItem = binding.toolbar.menu.findItem(R.id.action_settings)
        //settingsItem.isVisible = false

        //Handle actions of toolbar items
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    startActivity(Intent(activity, SettingsActivity::class.java))
                    true
                }
                R.id.add -> {
                    startActivity(Intent(activity, NewPostActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
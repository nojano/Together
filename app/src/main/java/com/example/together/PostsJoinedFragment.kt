package com.example.together

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.together.databinding.FragmentGroupsJoinedBinding


class PostsJoinedFragment : Fragment() {

    private var _binding: FragmentGroupsJoinedBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewJoinedPosts: RecyclerView

    private var layoutmanager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecycleAdapterJoinedPosts.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupsJoinedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewJoinedPosts = binding.recyclerViewPostJoined

        layoutmanager = LinearLayoutManager(this.context)
        recyclerViewJoinedPosts.layoutManager = layoutmanager

        adapter = RecycleAdapterJoinedPosts()
        recyclerViewJoinedPosts.adapter = adapter

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
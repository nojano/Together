package com.example.together

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout


class fragment_groups : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<View>(R.id.pager)
        val tabLayout : View? = view.findViewById(R.id.tab_layout)
        //tabLayout.setupViewPager(viewPager)

        //Setup the toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.homepage_toolbar)

        //Disabling search item
        val searchItem = toolbar.menu.findItem(R.id.search)
        searchItem.isVisible = false
        //Disabling add post item
        val addItem = toolbar.menu.findItem(R.id.add)
        addItem.isVisible = false
        //Disabling settings menu
        val settingsItem = toolbar.menu.findItem(R.id.action_settings)
        settingsItem.isVisible = false

        //Handle actions of toolbar items
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                else -> false
            }
        }
    }


    }

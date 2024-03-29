package com.example.together

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapterCreatedPosts : RecyclerView.Adapter<RecycleAdapterCreatedPosts.ViewHolder>() {

    private var titles = fillTitleCreatedPosts(createdPosts)
    private var descriptions = fillDescriptionCreatedPosts(createdPosts)
    private var cities = fillCityCreatedPosts(createdPosts)
    private var membersAlreadyIns = fillMembersAlreadyInCreatedPosts(createdPosts)
    private var membersWeNeeds = fillMembersWeNeedCreatedPosts(createdPosts)
    private var ownerNickname = fillMembersNickNameCreatedPosts(createdPosts)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapterCreatedPosts.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.actvity_created_groups_card, parent, false)
        return ViewHolder(v)
    }

     override fun onBindViewHolder(holder: RecycleAdapterCreatedPosts.ViewHolder, position: Int) {
        holder.title.text = titles[position]
        holder.description.text = descriptions[position]
        holder.city.text = cities[position]
        holder.membersAlreadyIn.text = membersAlreadyIns[position]
        holder.membersWeNeed.text = membersWeNeeds[position]
         holder.nickname.text = ownerNickname[position]

    }

    override fun getItemCount(): Int {
        //it will return the number of items that we pass to the viewholder
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profilePic: ImageView
        var nickname: TextView
        var city: TextView
        var title : TextView
        var description: TextView
        var membersWeNeed: TextView
        var membersAlreadyIn: TextView

        init {
            profilePic = itemView.findViewById(R.id.cardprofilepic)
            nickname = itemView.findViewById(R.id.cardnickname)
            city = itemView.findViewById(R.id.cardcity)
            title = itemView.findViewById(R.id.cardtitle)
            description = itemView.findViewById(R.id.carddescription)
            membersWeNeed = itemView.findViewById(R.id.neededMembers)
            membersAlreadyIn = itemView.findViewById(R.id.membersAlreadyIn)
        }

    }

}
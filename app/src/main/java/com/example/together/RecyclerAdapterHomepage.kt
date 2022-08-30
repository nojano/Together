package com.example.together

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterHomepage: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>() {

    private var titles = fillTitleArray(res)
    private var descriptions = fillDescriptionArray(res)
    private var cities = fillCityArray(res)
    private var membersAlreadyIns = fillMembersAlreadyInArray(res)
    private var membersWeNeeds = fillMembersWeNeedArray(res)
    private var ownerNickname = fillOwnerNicknameArray(res)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterHomepage.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_homepagecard, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterHomepage.ViewHolder, position: Int) {
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
        var title :TextView
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

package com.example.together

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterHomepage: RecyclerView.Adapter<RecyclerAdapterHomepage.ViewHolder>() {

    private var nicknames = arrayOf("davide", "gianmarco", "alessandro", "nick")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterHomepage.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_homepagecard, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterHomepage.ViewHolder, position: Int) {
        holder.nickname.text = nicknames[position]


    }

    override fun getItemCount(): Int {
        //it will return the number of items that we pass to the viewholder
        return nicknames.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var profilePic: ImageView
        var nickname: TextView
        var city: TextView
        var title :TextView
        var description: TextView
        var availableplaces: TextView
        var maxplaces: TextView

        init {
            profilePic = itemView.findViewById(R.id.cardprofilepic)
            nickname = itemView.findViewById(R.id.cardnickname)
            city = itemView.findViewById(R.id.cardcity)
            title = itemView.findViewById(R.id.cardtitle)
            description = itemView.findViewById(R.id.carddescription)
            availableplaces = itemView.findViewById(R.id.cardnumberavailable)
            maxplaces = itemView.findViewById(R.id.cardmaxplaces)
        }

    }
}
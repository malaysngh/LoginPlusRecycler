package com.example.loginplusrecycler.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginplusrecycler.R
import com.example.loginplusrecycler.UsersItem

class MyAdapter(private var con : Context, private var list : List<UsersItem>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        var img: ImageView = v.findViewById<ImageView>(R.id.RV_Image)
        var tvName: TextView = v.findViewById<TextView>(R.id.RV_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(con).inflate(R.layout.card_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(con).load(list[position].avatar_url).into(holder.img)

        holder.tvName.text = list[position].login

    }

    override fun getItemCount(): Int {
        return list.count()
    }

}
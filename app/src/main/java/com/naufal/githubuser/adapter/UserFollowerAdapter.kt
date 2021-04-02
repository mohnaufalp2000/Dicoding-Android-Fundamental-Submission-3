package com.naufal.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.githubuser.databinding.ItemListBinding
import com.naufal.githubuser.model.Follower

class UserFollowerAdapter(private val list: ArrayList<Follower>) : RecyclerView.Adapter<UserFollowerAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(list[position].avatarUrl)
            .into(holder.binding.imgProfileList)

        holder.binding.txtUsernameList.text = list[position].login
    }

    override fun getItemCount(): Int = list.size

}
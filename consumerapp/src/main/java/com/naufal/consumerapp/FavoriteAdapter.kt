package com.naufal.consumerapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.consumerapp.databinding.ItemListBinding


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var list = ArrayList<Favorite>()
    set(list) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtUsernameList.text = list[position].login

        Glide.with(holder.itemView.context)
            .load(list[position].avatarUrl)
            .into(holder.binding.imgProfileList)

    }

    override fun getItemCount(): Int = list.size
}
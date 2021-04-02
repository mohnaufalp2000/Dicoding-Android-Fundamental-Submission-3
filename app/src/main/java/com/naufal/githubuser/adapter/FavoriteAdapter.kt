package com.naufal.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naufal.githubuser.activity.DetailActivity
import com.naufal.githubuser.databinding.ItemListBinding
import com.naufal.githubuser.model.Favorite

class FavoriteAdapter(private val list : List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtUsernameList.text = list[position].login

        Glide.with(holder.itemView.context)
            .load(list[position].avatarUrl)
            .into(holder.binding.imgProfileList)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.apply {
                putExtra(DetailActivity.USER, list[position].login)
                putExtra(DetailActivity.ID, list[position].id)
                putExtra(DetailActivity.AVATAR, list[position].avatarUrl)
            }
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size
}
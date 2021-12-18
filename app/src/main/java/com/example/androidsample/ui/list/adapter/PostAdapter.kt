package com.example.androidsample.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsample.databinding.ItemPostBinding
import com.example.androidsample.model.Post

class PostAdapter(private val posts: MutableList<Post>) : RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Post>) {
        this.posts.clear()
        this.posts.addAll(list)
        notifyDataSetChanged()
    }
}

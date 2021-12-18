package com.example.androidsample.ui.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.androidsample.databinding.ItemPostBinding
import com.example.androidsample.model.Post

class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(post: Post) {
        post.apply {
            binding.tvTitle.text = post.title
            binding.tvBody.text = post.body
        }
    }
}

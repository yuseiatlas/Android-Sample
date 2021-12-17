package com.example.androidsample.repository

import com.example.androidsample.model.Post

interface ListRepository {
    suspend fun fetchPosts(): List<Post>
}

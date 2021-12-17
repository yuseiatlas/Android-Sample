package com.example.androidsample.network

import com.example.androidsample.GetAllPostsQuery

interface ApiService {
    suspend fun fetchPosts(): GetAllPostsQuery.Data?
}

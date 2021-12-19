package com.example.androidsample.repository

import com.example.androidsample.model.Post
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getPostById(postId: String): Flow<Post?>
}

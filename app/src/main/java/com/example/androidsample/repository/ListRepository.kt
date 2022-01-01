package com.example.androidsample.repository

import com.example.androidsample.model.Post
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    suspend fun refresh(): Flow<List<Post>>
}

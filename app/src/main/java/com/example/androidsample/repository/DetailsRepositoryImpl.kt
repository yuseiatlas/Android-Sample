package com.example.androidsample.repository

import com.example.androidsample.db.PostDao
import com.example.androidsample.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val postDao: PostDao
) : DetailsRepository {
    override fun getPostById(postId: String): Flow<Post?> = postDao.getPostById(postId)
}

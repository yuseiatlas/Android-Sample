package com.example.androidsample.repository

import com.example.androidsample.db.PostDao
import com.example.androidsample.mapper.PostMapper
import com.example.androidsample.model.Post
import com.example.androidsample.network.ApiService
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val postMapper: PostMapper,
) : ListRepository {

    override suspend fun getPosts() = postDao.getPosts()

    override suspend fun refresh(): List<Post> {
        postDao.clear()
        val posts = apiService.fetchPosts()?.posts?.data?.filterNotNull()?.map { postMapper.toPost(it) }.orEmpty()
        postDao.insertPosts(posts)
        return posts
    }
}

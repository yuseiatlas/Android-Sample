package com.example.androidsample.repository

import com.example.androidsample.mapper.PostMapper
import com.example.androidsample.model.Post
import com.example.androidsample.network.ApiService
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val postMapper: PostMapper
) : ListRepository {

    override suspend fun fetchPosts(): List<Post> {
        return apiService.fetchPosts()?.posts?.data?.filterNotNull()?.map { postMapper.toPost(it) }.orEmpty()
    }
}

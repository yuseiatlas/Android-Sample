package com.example.androidsample.repository

import com.example.androidsample.db.PostDao
import com.example.androidsample.mapper.PostMapper
import com.example.androidsample.model.Post
import com.example.androidsample.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val postDao: PostDao,
    private val postMapper: PostMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : ListRepository {

    override suspend fun refresh(): Flow<List<Post>> = flow {
        emit(postDao.getPosts())
        val posts = apiService.fetchPosts()?.posts?.data?.filterNotNull()?.map { postMapper.toPost(it) }.orEmpty()
        postDao.clear()
        postDao.insertPosts(posts)
        emit(posts)
    }.flowOn(coroutineDispatcher)
}

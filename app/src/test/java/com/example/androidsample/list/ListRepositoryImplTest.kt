package com.example.androidsample.list

import com.example.androidsample.GetAllPostsQuery
import com.example.androidsample.db.PostDao
import com.example.androidsample.mapper.PostMapper
import com.example.androidsample.model.Post
import com.example.androidsample.network.ApiService
import com.example.androidsample.repository.ListRepository
import com.example.androidsample.repository.ListRepositoryImpl
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ListRepositoryImplTest {
    private val apiService = mockk<ApiService>(relaxed = true)
    private val postDao = mockk<PostDao>(relaxed = true)
    private val postMapper = mockk<PostMapper>(relaxed = true)
    private val repository: ListRepository = ListRepositoryImpl(
        apiService = apiService,
        postDao = postDao,
        postMapper = postMapper
    )

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPosts returns the value from PostDao`() = runBlockingTest {
        val flow = mockk<Flow<List<Post>>>()
        ArrangeBuilder()
            .withPostFlow(flow)

        repository.getPosts() shouldBe flow
    }

    @Test
    fun `refresh with data received caches fetched posts`() = runBlockingTest {
        val post = mockk<Post>()
        val response = GetAllPostsQuery.Data(
            posts = GetAllPostsQuery.Posts(
                data = listOf(mockk()),
                meta = null
            )
        )
        ArrangeBuilder()
            .withSuccessRefresh(response)
            .withMappedPost(post)

        repository.refresh()
        coVerify(exactly = 1) { postDao.clear() }
        coVerify(exactly = 1) { postDao.insertPosts(listOf(post)) }
    }

    @Test
    fun `refresh without data received caches empty list`() = runBlockingTest {
        ArrangeBuilder()
            .withSuccessRefresh(null)

        repository.refresh()
        coVerify(exactly = 1) { postDao.clear() }
        coVerify(exactly = 1) { postDao.insertPosts(emptyList()) }
    }

    private inner class ArrangeBuilder {

        fun withPostFlow(flow: Flow<List<Post>>): ArrangeBuilder {
            every { postDao.getPosts() } returns flow
            return this
        }

        fun withSuccessRefresh(data: GetAllPostsQuery.Data?): ArrangeBuilder {
            coEvery { apiService.fetchPosts() } returns data
            return this
        }

        fun withMappedPost(post: Post) {
            every { postMapper.toPost(any()) } returns post
        }
    }
}

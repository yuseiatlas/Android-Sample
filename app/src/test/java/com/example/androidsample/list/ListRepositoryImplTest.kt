package com.example.androidsample.list

import app.cash.turbine.test
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
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ListRepositoryImplTest {
    private val apiService = mockk<ApiService>(relaxed = true)
    private val postDao = mockk<PostDao>(relaxed = true)
    private val postMapper = mockk<PostMapper>(relaxed = true)
    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: ListRepository = ListRepositoryImpl(
        apiService = apiService,
        postDao = postDao,
        postMapper = postMapper,
        coroutineDispatcher = testDispatcher
    )

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `refresh emits cached posts then received posts`() = runBlockingTest {
        val post = mockk<Post>()
        val cachedPosts = listOf<Post>(
            mockk(),
            mockk(),
        )
        val response = GetAllPostsQuery.Data(
            posts = GetAllPostsQuery.Posts(
                data = listOf(mockk()),
                meta = null
            )
        )
        ArrangeBuilder()
            .withCachedPosts(cachedPosts)
            .withSuccessRefresh(response)
            .withMappedPost(post)

        repository.refresh().test {
            awaitItem() shouldBe cachedPosts
            awaitItem() shouldBe listOf(post)
            awaitComplete()
        }
        coVerify(exactly = 1) { postDao.clear() }
        coVerify(exactly = 1) { postDao.insertPosts(listOf(post)) }
    }

    @Test
    fun `refresh with received posts caches newly fetched posts`() = runBlockingTest {
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

        fun withSuccessRefresh(data: GetAllPostsQuery.Data?): ArrangeBuilder {
            coEvery { apiService.fetchPosts() } returns data
            return this
        }

        fun withCachedPosts(posts: List<Post>): ArrangeBuilder {
            coEvery { postDao.getPosts() } returns posts
            return this
        }

        fun withMappedPost(post: Post) {
            every { postMapper.toPost(any()) } returns post
        }
    }
}

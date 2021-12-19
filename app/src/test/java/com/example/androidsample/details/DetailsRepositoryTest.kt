package com.example.androidsample.details

import com.example.androidsample.db.PostDao
import com.example.androidsample.model.Post
import com.example.androidsample.repository.DetailsRepository
import com.example.androidsample.repository.DetailsRepositoryImpl
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DetailsRepositoryTest {
    private val postDao = mockk<PostDao>()
    private val repository: DetailsRepository = DetailsRepositoryImpl(postDao)

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPostById returns the value from PostDao`() = runBlockingTest {
        val post = mockk<Post>()
        val id = "42"
        ArrangeBuilder()
            .withCachedPost("42", post)

        repository.getPostById(id).first() shouldBe post
        verify(exactly = 1) { postDao.getPostById(id) }
    }

    private inner class ArrangeBuilder {
        fun withCachedPost(postId: String, post: Post): ArrangeBuilder {
            every { postDao.getPostById(postId) } returns flow { emit(post) }
            return this
        }
    }
}

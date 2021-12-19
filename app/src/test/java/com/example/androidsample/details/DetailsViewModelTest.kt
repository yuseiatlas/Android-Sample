package com.example.androidsample.details

import app.cash.turbine.test
import com.example.androidsample.model.Post
import com.example.androidsample.repository.DetailsRepository
import com.example.androidsample.ui.details.DetailsState
import com.example.androidsample.ui.details.DetailsViewModel
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DetailsViewModelTest {
    private val repository = mockk<DetailsRepository>(relaxed = true)
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val viewModel = DetailsViewModel(
        postId = "42",
        repository = repository,
        coroutineScope = testScope,
    )

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `initial state is emitted on ViewModel's launch`() = testScope.runBlockingTest {
        ArrangeBuilder()

        viewModel.state.test {
            awaitItem() shouldBe DetailsState(post = null)
            expectNoEvents()
        }
    }

    @Test
    fun `new post on flow emits new state`() = testScope.runBlockingTest {
        val post = mockk<Post>()
        val builder = ArrangeBuilder()
            .withMutablePostsFlow("42")

        viewModel.state.test {
            viewModel.observePostFlow()
            builder.emitPosts(post)

            awaitItem().post.shouldBeNull() // initial posts
            awaitItem().post shouldBe post
            expectNoEvents()
        }
    }

    private inner class ArrangeBuilder {
        private val postsFlow = MutableStateFlow<Post?>(null)

        fun withMutablePostsFlow(postId: String): ArrangeBuilder {
            every { repository.getPostById(postId) } returns postsFlow
            return this
        }

        suspend fun emitPosts(post: Post) {
            postsFlow.emit(post)
        }

    }
}

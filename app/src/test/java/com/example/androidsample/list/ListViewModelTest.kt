package com.example.androidsample.list

import app.cash.turbine.test
import com.example.androidsample.model.Post
import com.example.androidsample.repository.ListRepository
import com.example.androidsample.ui.list.ListEffect.HandleThrowable
import com.example.androidsample.ui.list.ListState
import com.example.androidsample.ui.list.ListViewModel
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class ListViewModelTest {
    private val posts = listOf<Post>(mockk(), mockk())
    private val repository = mockk<ListRepository>(relaxed = true)
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val viewModel = ListViewModel(
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
            awaitItem() shouldBe ListState(
                posts = emptyList(),
                isLoading = false
            )
            expectNoEvents()
        }
    }

    @Test
    fun `new posts on flow emits new state`() = testScope.runBlockingTest {
        val builder = ArrangeBuilder()
            .withMutablePostsFlow()

        viewModel.state.test {
            viewModel.observePostsFlow()
            builder.emitPosts(posts)

            awaitItem().posts.shouldBeEmpty() // initial posts
            awaitItem().posts shouldBe posts
            expectNoEvents()
        }
    }

    @Test
    fun `refresh on success emits correct loading state`() = testScope.runBlockingTest {
        ArrangeBuilder()
            .withRefreshSuccess()

        viewModel.state.test {
            viewModel.refresh()

            awaitItem().isLoading shouldBe false // initial load
            awaitItem().isLoading shouldBe true
            awaitItem().isLoading shouldBe false
            expectNoEvents()
        }
    }

    @Test
    fun `refresh on failure emits correct loading states`() = testScope.runBlockingTest {
        ArrangeBuilder()
            .withRefreshFailure(Exception())

        viewModel.state.test {
            viewModel.refresh()

            awaitItem().isLoading shouldBe false // initial load
            awaitItem().isLoading shouldBe true
            awaitItem().isLoading shouldBe false
            expectNoEvents()
        }
    }

    @Test
    fun `refresh on failure emits HandleThrowable effect`() = testScope.runBlockingTest {
        val exception = Exception()
        ArrangeBuilder()
            .withRefreshFailure(exception)

        viewModel.effects.test {
            viewModel.refresh()

            awaitItem() shouldBe HandleThrowable(exception)
            expectNoEvents()
        }
    }

    private inner class ArrangeBuilder {
        private val postsFlow = MutableStateFlow<List<Post>>(emptyList())

        init {
            withPosts(emptyList())
        }

        fun withPosts(posts: List<Post>): ArrangeBuilder {
            coEvery { repository.getPosts() } returns flow { emit(posts) }
            return this
        }

        fun withMutablePostsFlow(): ArrangeBuilder {
            coEvery { repository.getPosts() } returns postsFlow
            return this
        }

        fun withRefreshSuccess(): ArrangeBuilder {
            coEvery { repository.refresh() } returns Unit
            return this
        }

        fun withRefreshFailure(throwable: Throwable): ArrangeBuilder {
            coEvery { repository.refresh() } throws throwable
            return this
        }

        suspend fun emitPosts(posts: List<Post>) {
            postsFlow.emit(posts)
        }
    }
}

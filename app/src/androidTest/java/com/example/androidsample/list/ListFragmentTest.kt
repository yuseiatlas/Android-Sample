package com.example.androidsample.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.androidsample.R
import com.example.androidsample.launchFragmentInHiltContainer
import com.example.androidsample.matchers.SwipeRefreshLayoutMatchers.isRefreshing
import com.example.androidsample.matchers.withRecyclerView
import com.example.androidsample.model.Post
import com.example.androidsample.ui.list.ListEffect
import com.example.androidsample.ui.list.ListFragment
import com.example.androidsample.ui.list.ListState
import com.example.androidsample.ui.list.ListViewModel
import com.example.androidsample.ui.list.adapter.PostViewHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.hamcrest.CoreMatchers.not
import org.junit.Test

class ListFragmentTest {
    private val viewModel = mockk<ListViewModel>(relaxed = true)

    @Test
    fun withLoad_swipeToRefreshLoaderIsShown() {
        ArrangeBuilder()
            .withState(createState(isLoading = true))
            .startFragment()

        onView(withId(R.id.swipeRefresh)).check(matches(isRefreshing()))
    }

    @Test
    fun withoutLoad_swipeToRefreshLoaderIsHidden() {
        ArrangeBuilder()
            .withState(createState())
            .startFragment()

        onView(withId(R.id.swipeRefresh)).check(matches(not(isRefreshing())))
    }

    @Test
    fun withPosts_emptyViewIsHiddenAndListIsPopulated() {
        val posts = listOf(
            createPost(
                "1",
                "My Way",
                """
                    And now, the end is near
                    And so I face the final curtain
                    My friend, I'll make it clear
                    I'll state my case, of which I am certain
                    """
            ),
            createPost(
                "2",
                "Cheek to Cheek"
            ),
        )
        ArrangeBuilder()
            .withState(createState(posts = posts))
            .startFragment()

        onView(withId(R.id.tvEmptyViewTitle)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvEmptyViewBody)).check(matches(not(isDisplayed())))
        onView(withId(R.id.ctaEmptyView)).check(matches(not(isDisplayed())))
        posts.forEachIndexed { index, post ->
            onView(
                withRecyclerView(R.id.rvPost)
                    .atPositionOnView(index, R.id.tvTitle)
            )
                .check(matches(withText(post.title)))
            onView(
                withRecyclerView(R.id.rvPost)
                    .atPositionOnView(index, R.id.tvBody)
            )
                .check(matches(withText(post.body.take(120)))) // max length of the body is 120
        }
    }

    @Test
    fun withPosts_clickingOnItem_passesPostToViewModel() {
        val posts = listOf(createPost("1"), createPost("2"))
        ArrangeBuilder()
            .withState(createState(posts = posts))
            .startFragment()

        onView(withId(R.id.rvPost)).perform(RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(0, click()))
        verify(exactly = 1) { viewModel.onItemClicked(posts.first()) }
    }

    @Test
    fun withoutPosts_emptyViewIsShown() {
        ArrangeBuilder()
            .withState(createState(posts = emptyList()))
            .startFragment()

        onView(withId(R.id.swipeRefresh)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvEmptyViewTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvEmptyViewBody)).check(matches(isDisplayed()))
        onView(withId(R.id.ctaEmptyView)).check(matches(isDisplayed()))
    }

    @Test
    fun withoutPosts_clickingOnEmptyViewCTA_refreshesList() {
        ArrangeBuilder()
            .withState(createState(posts = emptyList()))
            .startFragment()

        onView(withId(R.id.ctaEmptyView)).perform(click())
        verify { viewModel.refresh() }
    }

    private fun createState(
        posts: List<Post> = emptyList(),
        isLoading: Boolean = false
    ) = ListState(
        posts = posts,
        isLoading = isLoading
    )

    private fun createPost(
        id: String,
        title: String = "Title",
        body: String = "Body",
        author: String = "Author",
    ) = Post(
        id = id,
        title = title,
        body = body,
        author = author
    )

    private inner class ArrangeBuilder {
        private val effects = MutableSharedFlow<ListEffect>()

        init {
            every { viewModel.effects } returns effects
        }

        fun withState(state: ListState): ArrangeBuilder {
            every { viewModel.state } returns MutableStateFlow(state).asStateFlow()
            return this
        }

        suspend fun emitEffect(effect: ListEffect): ArrangeBuilder {
            effects.emit(effect)
            return this
        }

        fun startFragment() {
            launchFragmentInHiltContainer<ListFragment>(
                themeResId = R.style.Theme_AndroidSample,
                factory = ListFragmentFactory(
                    fakeViewModel = viewModel
                )
            )
        }
    }
}

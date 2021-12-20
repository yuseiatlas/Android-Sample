package com.example.androidsample.details

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.androidsample.R
import com.example.androidsample.launchFragmentInHiltContainer
import com.example.androidsample.model.Post
import com.example.androidsample.ui.details.DetailsFragment
import com.example.androidsample.ui.details.DetailsState
import com.example.androidsample.ui.details.DetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Test

class DetailsFragmentTest {
    private val viewModel = mockk<DetailsViewModel>(relaxed = true)

    @Test
    fun withPost_contentIsPopulated() {
        val post = Post(
            id = "1",
            title = "Hit the Road Jack",
            body = """
                Hit the road Jack and don't you come back
                No more, no more, no more, no more
                Hit the road Jack and don't you come back no more
                """,
            author = "Ray Charles"
        )
        ArrangeBuilder()
            .withState(DetailsState(post = post))
            .startFragment()

        onView(withId(R.id.tvTitle)).check(matches(withText(post.title)))
        onView(withId(R.id.tvAuthor)).check(matches(withText(post.author)))
        onView(withId(R.id.tvBody)).check(matches(withText(post.body)))
    }

    private inner class ArrangeBuilder {
        fun withState(state: DetailsState): ArrangeBuilder {
            every { viewModel.state } returns MutableStateFlow(state).asStateFlow()
            return this
        }

        fun startFragment() {
            launchFragmentInHiltContainer<DetailsFragment>(
                themeResId = R.style.Theme_AndroidSample,
                factory = DetailsFragmentFactory(
                    fakeViewModel = viewModel
                )
            )
        }
    }
}

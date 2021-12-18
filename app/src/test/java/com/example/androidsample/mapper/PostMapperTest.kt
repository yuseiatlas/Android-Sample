package com.example.androidsample.mapper

import com.example.androidsample.GetAllPostsQuery
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PostMapperTest {
    private val postMapper = PostMapper()
    private val post = GetAllPostsQuery.Data1(
        id = "42",
        title = "What's love?",
        body = "Baby don't hurt me. Don't hurt me. No more."
    )

    @Test
    fun `non-null data instance returns Post`() {
        val mappedPost = postMapper.toPost(post)

        mappedPost.apply {
            id shouldBe post.id
            title shouldBe post.title
            body shouldBe post.body
        }
    }

    @Test
    fun `null id should throw an NPE while mapping`() {
        val nullIdPost = post.copy(id = null)

        shouldThrowExactly<NullPointerException> {
            postMapper.toPost(nullIdPost)
        }
    }

    @Test
    fun `null title should throw an NPE while mapping`() {
        val nullTitlePost = post.copy(title = null)

        shouldThrowExactly<NullPointerException> {
            postMapper.toPost(nullTitlePost)
        }
    }

    @Test
    fun `null body should throw an NPE while mapping`() {
        val nullBodyPost = post.copy(body = null)

        shouldThrowExactly<NullPointerException> {
            postMapper.toPost(nullBodyPost)
        }
    }
}

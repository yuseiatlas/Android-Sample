package com.example.androidsample.mapper

import com.example.androidsample.GetAllPostsQuery
import com.example.androidsample.model.Post
import javax.inject.Inject

class PostMapper @Inject constructor() {

    fun toPost(post: GetAllPostsQuery.Data1) = Post(
        id = post.id!!,
        title = post.title!!,
        body = post.body!!,
        author = post.user!!.name!!,
        username = post.user.username!!,
    )
}

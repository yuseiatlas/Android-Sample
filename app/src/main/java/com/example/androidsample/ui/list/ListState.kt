package com.example.androidsample.ui.list

import com.example.androidsample.model.Post

data class ListState(
    val posts: List<Post>,
    val isLoading: Boolean
)

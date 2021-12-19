package com.example.androidsample.ui.list

import com.example.androidsample.model.Post

sealed class ListEffect {
    data class LaunchDetailsScreen(val post: Post) : ListEffect()
    data class HandleThrowable(val throwable: Throwable) : ListEffect()
}

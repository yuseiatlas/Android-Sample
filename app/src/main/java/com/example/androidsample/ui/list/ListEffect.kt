package com.example.androidsample.ui.list

sealed class ListEffect {
    data class HandleThrowable(val throwable: Throwable) : ListEffect()
}

package com.example.androidsample.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launch(
    providedScope: CoroutineScope?,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return (providedScope ?: viewModelScope).launch(block = block)
}

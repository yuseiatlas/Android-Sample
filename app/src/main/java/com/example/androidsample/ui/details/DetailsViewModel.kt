package com.example.androidsample.ui.details

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsample.extensions.launch
import com.example.androidsample.repository.DetailsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.lang.reflect.Modifier.PRIVATE

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val postId: String,
    private val repository: DetailsRepository,
    private val coroutineScope: CoroutineScope?
) : ViewModel() {
    private val _state = MutableStateFlow(getInitialState())
    val state = _state.asStateFlow()

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(postId: String): DetailsViewModel
    }

    init {
        observePostFlow()
    }

    private fun getInitialState() = DetailsState(post = null)

    @VisibleForTesting(otherwise = PRIVATE)
    fun observePostFlow() {
        launch(coroutineScope) {
            repository.getPostById(postId).collectLatest { post ->
                _state.emit(_state.value.copy(post = post))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: DetailsViewModelFactory,
            postId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(postId) as T
            }
        }
    }
}

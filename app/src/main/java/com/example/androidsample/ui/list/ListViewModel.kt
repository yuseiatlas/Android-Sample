package com.example.androidsample.ui.list

import androidx.lifecycle.ViewModel
import com.example.androidsample.extensions.launch
import com.example.androidsample.model.Post
import com.example.androidsample.repository.ListRepository
import com.example.androidsample.ui.list.ListEffect.HandleThrowable
import com.example.androidsample.ui.list.ListEffect.LaunchDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository,
    private val coroutineScope: CoroutineScope?
) : ViewModel() {
    private val _state = MutableStateFlow(getInitialState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ListEffect>()
    val effects = _effects.asSharedFlow()

    init {
        launch(coroutineScope) {
            repository.getPosts().collectLatest { posts ->
                _state.emit(
                    _state.value.copy(
                        posts = posts,
                    )
                )
            }

        }
    }

    private fun getInitialState() = ListState(
        posts = emptyList(),
        isLoading = false
    )

    fun refresh() {
        launch(coroutineScope) {
            _state.emit(_state.value.copy(isLoading = true))
            try {
                repository.refresh()

                _state.emit(_state.value.copy(isLoading = false))
            } catch (throwable: Throwable) {
                _state.emit(_state.value.copy(isLoading = false))

                throwable.printStackTrace()
                _effects.emit(HandleThrowable(throwable))
            }
        }
    }

    fun onItemClicked(post: Post) {
        launch(coroutineScope) {
            _effects.emit(LaunchDetailsScreen(post))
        }
    }
}

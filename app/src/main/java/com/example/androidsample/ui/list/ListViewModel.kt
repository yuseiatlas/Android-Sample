package com.example.androidsample.ui.list

import androidx.lifecycle.ViewModel
import com.example.androidsample.extensions.launch
import com.example.androidsample.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private fun getInitialState() = ListState(
        posts = emptyList(),
        isLoading = false
    )

    fun fetchPosts() {
        launch(coroutineScope) {
            _state.emit(_state.value.copy(isLoading = true))
            try {
                _state.emit(_state.value.copy(isLoading = false))

                val posts = repository.fetchPosts()
                _state.emit(_state.value.copy(posts = posts))
            } catch (throwable: Throwable) {
                _state.emit(_state.value.copy(isLoading = false))

                throwable.printStackTrace()
                _effects.emit(ListEffect.HandleThrowable(throwable))
            }
        }
    }

}

package com.example.androidsample.ui.list

import androidx.lifecycle.ViewModel
import com.example.androidsample.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository
) : ViewModel() {
}

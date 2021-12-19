package com.example.androidsample.ui.details

import androidx.lifecycle.ViewModel
import com.example.androidsample.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel()

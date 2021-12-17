package com.example.androidsample.repository

import com.example.androidsample.network.ApiService
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DetailsRepository {
}

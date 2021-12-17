package com.example.androidsample.repository

import com.example.androidsample.network.ApiService
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ListRepository {
}

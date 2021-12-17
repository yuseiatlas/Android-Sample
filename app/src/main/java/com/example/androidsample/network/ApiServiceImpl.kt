package com.example.androidsample.network

import com.apollographql.apollo3.ApolloClient
import com.example.androidsample.GetAllPostsQuery

class ApiServiceImpl(
    private val apolloClient: ApolloClient
) : ApiService {

    override suspend fun fetchPosts(): GetAllPostsQuery.Data? = apolloClient.query(GetAllPostsQuery()).execute().data
}

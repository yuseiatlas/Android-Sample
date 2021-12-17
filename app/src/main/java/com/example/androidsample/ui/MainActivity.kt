package com.example.androidsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo3.ApolloClient
import com.example.androidsample.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

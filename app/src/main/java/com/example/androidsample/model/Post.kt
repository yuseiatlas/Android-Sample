package com.example.androidsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val author: String,
    val username: String
)

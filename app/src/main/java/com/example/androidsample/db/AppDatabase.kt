package com.example.androidsample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidsample.model.Post

@Database(
    entities = [Post::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}

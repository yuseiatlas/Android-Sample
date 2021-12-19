package com.example.androidsample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.androidsample.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostById(postId: String): Flow<Post?>

    @Insert(onConflict = REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM post")
    suspend fun clear()
}

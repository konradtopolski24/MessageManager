package com.konradt.messagemanager.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.konradt.messagemanager.models.Post

@Dao
interface PostDao {

    @Insert
    suspend fun insertPost(post: Post): Long

    @Insert
    suspend fun insertPosts(posts: List<Post>)

    @Update
    suspend fun updatePost(post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<List<Post>>

    @Query("SELECT * FROM posts WHERE id==:id")
    fun getPost(id: Int): LiveData<Post>
}
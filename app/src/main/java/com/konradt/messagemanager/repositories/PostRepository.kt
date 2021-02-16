package com.konradt.messagemanager.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.konradt.messagemanager.models.Post
import com.konradt.messagemanager.room.PostDao
import com.konradt.messagemanager.room.PostDatabase

class PostRepository(application: Application) {

    private val postDao: PostDao

    init {
        val db = PostDatabase.getInstance(application)
        postDao = db?.postDao()!!
    }

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    suspend fun updatePost(post: Post) {
        postDao.updatePost(post)
    }

    fun getPost(id: Int): LiveData<Post> {
        return postDao.getPost(id)
    }

    fun getAllPosts(): LiveData<List<Post>> {
        return postDao.getAllPosts()
    }
}
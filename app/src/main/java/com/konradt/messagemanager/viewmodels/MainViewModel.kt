package com.konradt.messagemanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.konradt.messagemanager.models.Post
import com.konradt.messagemanager.repositories.PostRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepository(application)
    private val posts = repository.getAllPosts()

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }
}
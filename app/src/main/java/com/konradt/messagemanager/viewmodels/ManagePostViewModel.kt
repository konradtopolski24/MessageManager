package com.konradt.messagemanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.konradt.messagemanager.databinding.ActivityManagePostBinding
import com.konradt.messagemanager.models.Post
import com.konradt.messagemanager.repositories.PostRepository
import kotlinx.coroutines.launch

class ManagePostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepository(application)
    private lateinit var post: LiveData<Post>

    fun getPost(id: Int): LiveData<Post> {
        post = repository.getPost(id)
        return post
    }

    fun insertPost(post: Post) = viewModelScope.launch {
        repository.insertPost(post)
    }

    fun updatePost(post: Post) = viewModelScope.launch {
        repository.updatePost(post)
    }

    fun bindViews(binding: ActivityManagePostBinding, post: Post) {
        binding.etIcon.setText(post.icon)
        binding.etTitle.setText(post.title)
        binding.etDescription.setText(post.description)
    }

    fun prepareNewPost(id: Int, binding: ActivityManagePostBinding): Post {
        return Post(
            id,
            binding.etTitle.text.toString(),
            binding.etDescription.text.toString(),
            binding.etIcon.text.toString()
        )
    }
}
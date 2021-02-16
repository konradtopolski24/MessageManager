package com.konradt.messagemanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.konradt.messagemanager.databinding.ActivityShowPostBinding
import com.konradt.messagemanager.models.Post
import com.konradt.messagemanager.repositories.PostRepository
import com.squareup.picasso.Picasso

class ShowPostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepository(application)
    private lateinit var post: LiveData<Post>

    fun getPost(id: Int): LiveData<Post> {
        post = repository.getPost(id)
        return post
    }

    fun bindViews(binding: ActivityShowPostBinding, post: Post) {
        Picasso.get().load(post.icon).into(binding.ivIcon)
        binding.tvTitle.text = post.title
        binding.tvDescription.text = post.description
        binding.tvAmount.text = post.description.count().toString()
    }
}
package com.konradt.messagemanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradt.messagemanager.databinding.ItemPostBinding
import com.konradt.messagemanager.models.Post
import com.squareup.picasso.Picasso

class PostAdapter(private val postListener: PostListener) :
    RecyclerView.Adapter<PostAdapter.PostHolder>() {

    private var posts: List<Post>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(layoutInflater, parent, false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        if (posts != null) {
            val post = posts!![position]
            holder.bindData(post)
        }
    }

    override fun getItemCount(): Int {
        return if (this.posts != null) posts!!.size else 0
    }

    fun changeData(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(post: Post) {
            binding.tvName.text = post.title
            Picasso.get().load(post.icon).into(binding.ivIcon)
            binding.clItem.setOnClickListener {
                postListener.onClickItem(post.id)
            }
        }
    }

    interface PostListener {
        fun onClickItem(id: Int)
    }
}
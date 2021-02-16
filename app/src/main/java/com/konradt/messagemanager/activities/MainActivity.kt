package com.konradt.messagemanager.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradt.messagemanager.R
import com.konradt.messagemanager.adapters.PostAdapter
import com.konradt.messagemanager.databinding.ActivityMainBinding
import com.konradt.messagemanager.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), PostAdapter.PostListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = PostAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareViewModel()
        prepareRv()
        prepareFab()
        observePost()
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun prepareBinding() {
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
        }
    }

    private fun prepareRv() {
        adapter
        val manager = LinearLayoutManager(this).apply {
            this.orientation = LinearLayoutManager.VERTICAL
        }
        binding.rvPost.apply {
            this.layoutManager = manager
            this.adapter = this@MainActivity.adapter
        }
    }

    private fun prepareFab() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, ManagePostActivity::class.java)
            intent.putExtra(ManagePostActivity.ID_EDIT, -1)
            startActivity(intent)
        }
    }

    private fun observePost() {
        viewModel.getPosts().observe(this, { posts -> adapter.changeData(posts) })
    }

    override fun onClickItem(id: Int) {
        val intent = Intent(this, ShowPostActivity::class.java)
        intent.putExtra(ShowPostActivity.ID_POST, id)
        startActivity(intent)
    }
}
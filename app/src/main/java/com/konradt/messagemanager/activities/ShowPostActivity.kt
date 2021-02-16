package com.konradt.messagemanager.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.konradt.messagemanager.R
import com.konradt.messagemanager.databinding.ActivityShowPostBinding
import com.konradt.messagemanager.viewmodels.ShowPostViewModel

class ShowPostActivity : AppCompatActivity() {

    private lateinit var viewModel: ShowPostViewModel
    private lateinit var binding: ActivityShowPostBinding
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareViewModel()
        prepareId()
        prepareIb()
        observePost()
    }

    private fun prepareBinding() {
        binding = DataBindingUtil.setContentView<ActivityShowPostBinding>(
            this, R.layout.activity_show_post
        ).apply {
            this.lifecycleOwner = this@ShowPostActivity
        }
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(ShowPostViewModel::class.java)
    }

    private fun prepareId() {
        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt(ID_POST)
    }

    private fun prepareIb() {
        binding.ibEdit.setOnClickListener {
            val intent = Intent(this, ManagePostActivity::class.java)
            intent.putExtra(ManagePostActivity.ID_EDIT, id)
            startActivity(intent)
        }
    }

    private fun observePost() {
        viewModel.getPost(id)
            .observe(this, { post -> viewModel.bindViews(binding, post) })
    }

    companion object {
        const val ID_POST = "ID_POST"
    }
}
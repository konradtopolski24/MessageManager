package com.konradt.messagemanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.konradt.messagemanager.R
import com.konradt.messagemanager.databinding.ActivityManagePostBinding
import com.konradt.messagemanager.viewmodels.ManagePostViewModel

class ManagePostActivity : AppCompatActivity() {

    private lateinit var viewModel: ManagePostViewModel
    private lateinit var binding: ActivityManagePostBinding
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBinding()
        prepareViewModel()
        prepareId()
        prepareFab()
        observePost()
    }

    private fun prepareBinding() {
        binding = DataBindingUtil.setContentView<ActivityManagePostBinding>(
            this, R.layout.activity_manage_post
        ).apply {
            this.lifecycleOwner = this@ManagePostActivity
        }
    }

    private fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(ManagePostViewModel::class.java)
    }

    private fun prepareId() {
        val bundle: Bundle? = intent.extras
        id = bundle!!.getInt(ID_EDIT)
    }

    private fun prepareFab() {
        binding.fabFinish.setOnClickListener {
            if (id > 0) updatePost() else insertPost()
            finish()
        }
    }

    private fun insertPost() {
        viewModel.insertPost(viewModel.prepareNewPost(0, binding))
    }

    private fun updatePost() {
        viewModel.updatePost(viewModel.prepareNewPost(id, binding))
    }

    private fun observePost() {
        if (id > 0) viewModel.getPost(id)
            .observe(this, { post -> viewModel.bindViews(binding, post) })
    }

    companion object {
        const val ID_EDIT = "ID_EDIT"
    }
}
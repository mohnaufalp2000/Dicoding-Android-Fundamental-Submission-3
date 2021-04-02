package com.naufal.githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.R
import com.naufal.githubuser.adapter.UserAdapter
import com.naufal.githubuser.databinding.ActivityTopUserBinding
import com.naufal.githubuser.viewmodel.HomeViewModel

class TopUserActivity : AppCompatActivity() {

    private val binding by lazy{ActivityTopUserBinding.inflate(layoutInflater)}
    private val mHomeViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
        HomeViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showTopUserList()
        setupToolbar()

    }

    private fun showTopUserList() {
        mHomeViewModel.getUserHomeViewModel().observe(this,{
            val adapter = UserAdapter(it)
            binding.apply {
                rvTopUser.setHasFixedSize(true)
                rvTopUser.layoutManager = LinearLayoutManager(this@TopUserActivity)
                rvTopUser.adapter = adapter
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbTopUser)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.top_user)
        }
        binding.tbTopUser.setNavigationOnClickListener {
            finish()
        }
    }


}
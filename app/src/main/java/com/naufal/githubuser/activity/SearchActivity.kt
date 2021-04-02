package com.naufal.githubuser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.adapter.UserAdapter
import com.naufal.githubuser.databinding.ActivitySearchBinding
import com.naufal.githubuser.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val mSearchViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
        SearchViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupSearchView()

    }

    private fun setupSearchView() {

        binding.edtSearchUser.requestFocus()
        binding.edtSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchViewModel.setSearchViewModelUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        mSearchViewModel.getSearchViewModelUser().observe(this, {
            if(it?.size == 0){
                binding.notFound.visibility = View.VISIBLE
                binding.rvHome.visibility = View.GONE
            } else {
                binding.notFound.visibility = View.GONE
                binding.rvHome.visibility = View.VISIBLE
                val adapter = UserAdapter(it)
                binding.apply {
                    rvHome.setHasFixedSize(true)
                    rvHome.layoutManager = LinearLayoutManager(this@SearchActivity)
                    rvHome.adapter = adapter
                }
                adapter.notifyDataSetChanged()
            }
        })

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbSearch)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbSearch.setNavigationOnClickListener {
            finish()
        }
    }


}
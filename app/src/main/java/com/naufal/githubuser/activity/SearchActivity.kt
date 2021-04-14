package com.naufal.githubuser.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.adapter.UserAdapter
import com.naufal.githubuser.databinding.ActivitySearchBinding
import com.naufal.githubuser.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val mSearchViewModel by lazy { ViewModelProvider(
        this,
        ViewModelProvider.NewInstanceFactory()
    ).get(
        SearchViewModel::class.java
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupSearchView()

    }

    private fun setupSearchView() {

        binding.edtSearchUser.requestFocus()
        binding.edtSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mSearchViewModel.setSearchViewModelUser(query, this@SearchActivity)
                binding.apply {
                    findUser.visibility = View.GONE
                    pbSearch.visibility = View.VISIBLE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.edtSearchUser.setOnCloseListener {
            Toast.makeText(this, "close", Toast.LENGTH_LONG).show()

            false
        }

        mSearchViewModel.getSearchViewModelUser().observe(this, {
            if (it?.size == 0) {
                binding.apply {
                    findUser.visibility = View.GONE
                    notFound.visibility = View.VISIBLE
                    rvHome.visibility = View.GONE
                    pbSearch.visibility = View.GONE
                }
            } else {
                binding.apply {
                    findUser.visibility = View.GONE
                    notFound.visibility = View.GONE
                    rvHome.visibility = View.VISIBLE
                    pbSearch.visibility = View.GONE
                }
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
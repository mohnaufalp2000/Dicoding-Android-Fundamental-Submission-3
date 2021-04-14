package com.naufal.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.R
import com.naufal.githubuser.activity.DetailActivity
import com.naufal.githubuser.adapter.UserFollowerAdapter
import com.naufal.githubuser.databinding.FragmentFollowerBinding
import com.naufal.githubuser.viewmodel.DetailViewModel

class FollowerFragment : Fragment() {

    private var binding : FragmentFollowerBinding? = null
    private val mFollowerViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra(DetailActivity.USER)

        showListFollower(username)

    }

    private fun showListFollower(username: String?) {

        mFollowerViewModel.getFollowerViewModel(username).observe(viewLifecycleOwner, {
            val adapter = UserFollowerAdapter(it)
            binding?.apply {
                pbFollower.visibility = View.GONE
                rvFollower.setHasFixedSize(true)
                rvFollower.layoutManager = LinearLayoutManager(context)
                rvFollower.adapter = adapter
                rvFollower.isNestedScrollingEnabled = true
            }
            adapter.notifyDataSetChanged()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
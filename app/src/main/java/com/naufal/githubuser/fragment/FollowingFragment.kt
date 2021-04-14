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
import com.naufal.githubuser.adapter.UserFollowingAdapter
import com.naufal.githubuser.databinding.FragmentFollowerBinding
import com.naufal.githubuser.databinding.FragmentFollowingBinding
import com.naufal.githubuser.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {

    private var binding : FragmentFollowingBinding? = null
    private val mFollowingViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
        DetailViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra(DetailActivity.USER)

        showFollowingList(username)
    }

    private fun showFollowingList(username: String?) {

        mFollowingViewModel.getFollowingViewModel(username).observe(viewLifecycleOwner, {
            val adapter = UserFollowingAdapter(it)
            binding?.apply {
                pbFollowing.visibility = View.GONE
                rvFollowing.setHasFixedSize(true)
                rvFollowing.layoutManager = LinearLayoutManager(context)
                rvFollowing.adapter = adapter
                rvFollowing.isNestedScrollingEnabled = true
            }
            adapter.notifyDataSetChanged()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
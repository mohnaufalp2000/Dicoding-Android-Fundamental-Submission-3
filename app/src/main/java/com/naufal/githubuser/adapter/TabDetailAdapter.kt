package com.naufal.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.naufal.githubuser.fragment.FollowerFragment
import com.naufal.githubuser.fragment.FollowingFragment

class TabDetailAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {

    private val fragmentList: ArrayList<Fragment> = arrayListOf(
        FollowerFragment(),
        FollowingFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}

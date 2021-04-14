package com.naufal.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.activity.SearchActivity
import com.naufal.githubuser.activity.TopUserActivity
import com.naufal.githubuser.adapter.UserAdapter
import com.naufal.githubuser.databinding.FragmentHomeBinding
import com.naufal.githubuser.model.ItemsItem
import com.naufal.githubuser.viewmodel.HomeViewModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var binding : FragmentHomeBinding? = null
    private val mHomeViewModel by lazy { ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.edtSearchUser?.setOnClickListener {
            val intentSearch = Intent(context, SearchActivity::class.java)
            startActivity(intentSearch)
        }

        dateSetup()
        refreshPage()
        userListSetup()
        openTopUserList()
    }

    private fun openTopUserList() {
        binding?.txtViewMore?.setOnClickListener {
            val intent = Intent(context, TopUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun refreshPage() {
        binding?.swHome?.setOnRefreshListener {
            binding?.swHome?.isRefreshing = false
            dateSetup()
            userListSetup()
        }
    }

    private fun dateSetup() {
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)

        binding?.txtDate?.text = currentDate
    }

    private fun userListSetup() {
        mHomeViewModel.getUserHomeViewModel(context).observe(viewLifecycleOwner, {
            binding?.apply {
                shimmerRvUserList.stopShimmerAnimation()
                shimmerRvUserList.visibility = View.GONE
                rvUserList.visibility = View.VISIBLE
                txtViewMore.visibility = View.VISIBLE
            }
            val list = ArrayList<ItemsItem>()
            list.clear()
            for (i in 0..2){
                val listUser : ItemsItem = (it?.get(i) ?: 1) as ItemsItem
                list.add(listUser)
            }

            val adapter = UserAdapter(list)
            binding?.apply {
                rvUserList.setHasFixedSize(true)
                rvUserList.layoutManager = LinearLayoutManager(context)
                rvUserList.adapter = adapter
            }
            adapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        binding?.shimmerRvUserList?.startShimmerAnimation()
    }

    override fun onPause() {
        binding?.shimmerRvUserList?.stopShimmerAnimation()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}
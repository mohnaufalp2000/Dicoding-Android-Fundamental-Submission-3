package com.naufal.githubuser.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.adapter.FavoriteAdapter
import com.naufal.githubuser.databinding.FragmentFavoriteBinding
import com.naufal.githubuser.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var binding : FragmentFavoriteBinding? = null
    private val mFavoriteViewModel by lazy { ViewModelProvider(this).get(FavoriteViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavoriteViewModel.getAllData()?.observe(viewLifecycleOwner,{

            if (it.isNotEmpty()){
                binding?.apply {
                    emptyUser.visibility = View.GONE
                    rvFavorite.visibility = View.VISIBLE

                    val adapter = FavoriteAdapter(it)
                    binding?.apply {
                        rvFavorite.setHasFixedSize(true)
                        rvFavorite.layoutManager = LinearLayoutManager(context)
                        rvFavorite.adapter = adapter
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                binding?.apply {
                    emptyUser.visibility = View.VISIBLE
                    rvFavorite.visibility = View.GONE
                }
            }



        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
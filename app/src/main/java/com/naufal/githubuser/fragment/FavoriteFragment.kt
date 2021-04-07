package com.naufal.githubuser.fragment

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.adapter.FavoriteAdapter
import com.naufal.githubuser.database.FavoriteHelper
import com.naufal.githubuser.databinding.FragmentFavoriteBinding
import com.naufal.githubuser.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var binding : FragmentFavoriteBinding? = null

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

        GlobalScope.launch(Dispatchers.Main) {
            val databaseHelper = context?.let { FavoriteHelper.getInstance(it) }
            databaseHelper?.open()
            val deffered = async(Dispatchers.IO) {
                val cursor = databaseHelper?.getFavorites()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favoriteList = deffered.await()
            val adapter = FavoriteAdapter(favoriteList)
            if (favoriteList.isNotEmpty()){
                binding?.apply {
                    emptyUser.visibility = View.GONE
                    rvFavorite.visibility = View.VISIBLE
                    rvFavorite.setHasFixedSize(true)
                    rvFavorite.layoutManager = LinearLayoutManager(context)
                    rvFavorite.adapter = adapter
                }
            } else {
                binding?.apply {
                    emptyUser.visibility = View.VISIBLE
                    rvFavorite.visibility = View.GONE
                }
            }
            databaseHelper?.close()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
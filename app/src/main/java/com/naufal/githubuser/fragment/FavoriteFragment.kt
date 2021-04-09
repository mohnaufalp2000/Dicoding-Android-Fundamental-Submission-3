package com.naufal.githubuser.fragment

import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.githubuser.adapter.FavoriteAdapter
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.naufal.githubuser.database.FavoriteHelper
import com.naufal.githubuser.databinding.FragmentFavoriteBinding
import com.naufal.githubuser.helper.MappingHelper
import com.naufal.githubuser.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private var binding : FragmentFavoriteBinding? = null
    private val adapter = FavoriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                      loadNotesAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadNotesAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)?.also { adapter.list = it }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(EXTRA_STATE, adapter.list)
        super.onSaveInstanceState(outState)
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val databaseHelper = context?.let { FavoriteHelper.getInstance(it) }
            databaseHelper?.open()
            val deffered = async(Dispatchers.IO) {
                val cursor = activity?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favoriteList = deffered.await()

            adapter.list.addAll(favoriteList)
            if (favoriteList.isNotEmpty()){
                binding?.apply {
                    emptyUser.visibility = View.GONE
                    rvFavorite.visibility = View.VISIBLE
                    rvFavorite.setHasFixedSize(true)
                    rvFavorite.layoutManager = LinearLayoutManager(context)
                    rvFavorite.adapter = adapter
                }
                adapter.notifyDataSetChanged()
            } else {
                binding?.apply {
                    emptyUser.visibility = View.VISIBLE
                    rvFavorite.visibility = View.GONE
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
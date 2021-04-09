package com.naufal.consumerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.consumerapp.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.naufal.consumerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    private val adapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadNotesAsync()

    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deffered = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favoriteList = deffered.await()

            adapter.list.addAll(favoriteList)

            binding.apply {
                rvFavConsumer.setHasFixedSize(true)
                rvFavConsumer.layoutManager = LinearLayoutManager(this@MainActivity)
                rvFavConsumer.adapter = adapter
            }

        }
    }

}
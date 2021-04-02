package com.naufal.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.naufal.githubuser.database.ConfigDatabase
import com.naufal.githubuser.model.Favorite
import com.naufal.githubuser.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDao = ConfigDatabase.getDatabase(application)?.favoriteDao()
    private val repository = favoriteDao?.let { UserRepository(it) }

    fun getAllData() : LiveData<List<Favorite>>?{
        return repository?.getAllFavorite()
    }

    fun addFavorite(username: String?, id: Int, avatar: String?){
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = Favorite(
                id,
                username,
                avatar
            )
            repository?.addFavorite(favorite)
        }
    }

    fun selectFavorite(id: Int) = repository?.selectFavorite(id)

    fun deleteFavorite(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository?.deleteFavorite(id)
        }
    }

}
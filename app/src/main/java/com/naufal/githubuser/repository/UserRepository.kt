package com.naufal.githubuser.repository

import androidx.lifecycle.LiveData
import com.naufal.githubuser.database.FavoriteDao
import com.naufal.githubuser.model.Favorite

class UserRepository(private val favoriteDao: FavoriteDao) {

    fun getAllFavorite() : LiveData<List<Favorite>>{
        return favoriteDao.getAllFavorite()
    }

    suspend fun addFavorite(favorite: Favorite){
        favoriteDao.addFavorite(favorite)
    }

    fun selectFavorite(id: Int) = favoriteDao.selectFavorite(id)

    suspend fun deleteFavorite(id: Int){
        favoriteDao.deleteFavorite(id)
    }

}
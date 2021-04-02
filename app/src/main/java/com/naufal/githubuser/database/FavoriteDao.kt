package com.naufal.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.naufal.githubuser.model.Favorite

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavorite(favorite : Favorite)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorite() : LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite_table WHERE favorite_table.id=:id")
    fun selectFavorite(id: Int) : Int

    @Query("DELETE FROM favorite_table WHERE favorite_table.id=:id")
    suspend fun deleteFavorite(id: Int) : Int

}
package com.naufal.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naufal.githubuser.model.Favorite

@Database( entities = [Favorite::class], version = 1, exportSchema = false)
abstract class ConfigDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteDao

    companion object{

        private var INSTANCE : ConfigDatabase? = null

        fun getDatabase(context: Context) : ConfigDatabase? {
            return if(INSTANCE!=null){
                INSTANCE
            } else {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ConfigDatabase::class.java, "user_database").fallbackToDestructiveMigration().build()
                    INSTANCE
                }
            }

        }

    }

}
package com.naufal.githubuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.AVATARURL
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.DATE

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 3
        private const val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${ID} INTEGER PRIMARY KEY," +
                " $LOGIN TEXT NOT NULL," +
                " $AVATARURL TEXT NOT NULL," +
                " $DATE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}
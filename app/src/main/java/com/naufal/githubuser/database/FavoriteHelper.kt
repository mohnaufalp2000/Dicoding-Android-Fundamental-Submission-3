package com.naufal.githubuser.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.AVATARURL
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.DATE
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import kotlin.jvm.Throws

class FavoriteHelper(context: Context) {

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var sqLiteDatabase: SQLiteDatabase

        fun getInstance(context: Context) : FavoriteHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: FavoriteHelper(context)
            }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }


    @Throws(SQLException::class)
    fun open(){
        sqLiteDatabase = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (sqLiteDatabase.isOpen){
            sqLiteDatabase.close()
        }
    }

    fun addFavorite(id: Int, username: String?, avatar: String?, date: String?) : Long{
        val values = ContentValues().apply {
            put(ID, id)
            put(LOGIN, username)
            put(AVATARURL, avatar)
            put(DATE, date)
        }
        return sqLiteDatabase.insert(DATABASE_TABLE, null, values)
    }

    fun getFavorites() : Cursor {
        return sqLiteDatabase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$DATE ASC"
        )
    }

    fun isFavorite(id: Int) : Cursor{
        return sqLiteDatabase.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
    }

    fun removeFavorite(id: Int): Int {
        return sqLiteDatabase.delete(DATABASE_TABLE, "$ID = $id", null)
    }



}
package com.naufal.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.naufal.githubuser.database.DatabaseContract.AUTHORITY
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.naufal.githubuser.database.FavoriteHelper

class ContentProvider : ContentProvider() {

    companion object{
        private const val FAVORITE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoriteHelper: FavoriteHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
        }

    }

    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)){
            FAVORITE -> favoriteHelper.getFavorites()
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}
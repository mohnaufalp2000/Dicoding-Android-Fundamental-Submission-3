package com.naufal.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.naufal.githubuser"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object{
            const val TABLE_NAME = "favorite_table"
            const val ID = "id"
            const val LOGIN = "login"
            const val AVATARURL = "avatarUrl"
            const val DATE = "date"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
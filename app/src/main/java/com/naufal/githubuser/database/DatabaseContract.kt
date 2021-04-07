package com.naufal.githubuser.database

import android.provider.BaseColumns

class DatabaseContract {

    class FavoriteColumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "favorite_table"
            const val ID = "id"
            const val LOGIN = "login"
            const val AVATARURL = "avatarUrl"
            const val DATE = "date"
        }
    }

}
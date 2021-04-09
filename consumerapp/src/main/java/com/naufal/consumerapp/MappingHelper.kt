package com.naufal.consumerapp

import android.database.Cursor
import com.naufal.consumerapp.DatabaseContract.FavoriteColumns.Companion.AVATARURL
import com.naufal.consumerapp.DatabaseContract.FavoriteColumns.Companion.DATE
import com.naufal.consumerapp.DatabaseContract.FavoriteColumns.Companion.ID
import com.naufal.consumerapp.DatabaseContract.FavoriteColumns.Companion.LOGIN


object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<Favorite> {
        val list = ArrayList<Favorite>()

        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(LOGIN))
                val avatar = getString(getColumnIndexOrThrow(AVATARURL))
                val date = getString(getColumnIndexOrThrow(DATE))

                list.add(Favorite(id, username, avatar, date))
            }
        }
        return list
    }
}
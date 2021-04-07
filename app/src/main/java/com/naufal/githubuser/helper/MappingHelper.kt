package com.naufal.githubuser.helper

import android.database.Cursor
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.AVATARURL
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.DATE
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.ID
import com.naufal.githubuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import com.naufal.githubuser.model.Favorite

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?) : ArrayList<Favorite>{
        val list = ArrayList<Favorite>()

        cursor?.apply {
            while (moveToNext()){
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
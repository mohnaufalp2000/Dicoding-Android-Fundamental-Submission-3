package com.naufal.githubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize
data class Favorite(
    @PrimaryKey
    var id : Int = 0,
    var login: String? = null,
    var avatarUrl : String? = null
) : Parcelable
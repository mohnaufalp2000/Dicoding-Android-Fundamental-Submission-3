package com.naufal.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    var id : Int = 0,
    var login: String? = null,
    var avatarUrl : String? = null,
    var date : String? = null
) : Parcelable
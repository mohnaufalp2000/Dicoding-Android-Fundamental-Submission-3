package com.naufal.githubuser.network

import com.naufal.githubuser.model.*
import com.naufal.githubuser.network.ConfigNetwork.Companion.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun searchDataUser( @Query("q") name : String) : Call<Search>

    @GET("users")
    @Headers("Authorization: token $TOKEN")
    fun getListDataUser() : Call<ArrayList<ItemsItem>>

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getDetailDataUser(@Path("username") username : String?) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollowerUser(@Path("username") username : String?) : Call<ArrayList<Follower>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowingUser(@Path("username") username : String?) : Call<ArrayList<Following>>

}
package com.naufal.githubuser.network

import com.naufal.githubuser.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    @Headers("Authorization: token  759312239a2c6f79b744fab02094e8e7c4e9b3f4")
    fun searchDataUser( @Query("q") name : String) : Call<Search>

    @GET("users")
    @Headers("Authorization: token  759312239a2c6f79b744fab02094e8e7c4e9b3f4")
    fun getListDataUser() : Call<ArrayList<ItemsItem>>

    @GET("users/{username}")
    @Headers("Authorization: token  759312239a2c6f79b744fab02094e8e7c4e9b3f4")
    fun getDetailDataUser(@Path("username") username : String?) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token  759312239a2c6f79b744fab02094e8e7c4e9b3f4")
    fun getFollowerUser(@Path("username") username : String?) : Call<ArrayList<Follower>>

    @GET("users/{username}/following")
    @Headers("Authorization: token  759312239a2c6f79b744fab02094e8e7c4e9b3f4")
    fun getFollowingUser(@Path("username") username : String?) : Call<ArrayList<Following>>

}
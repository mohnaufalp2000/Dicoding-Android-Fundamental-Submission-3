package com.naufal.githubuser.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naufal.githubuser.activity.DetailActivity
import com.naufal.githubuser.activity.MainActivity
import com.naufal.githubuser.model.DetailUser
import com.naufal.githubuser.model.Follower
import com.naufal.githubuser.model.Following
import com.naufal.githubuser.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val listUser = MutableLiveData<DetailUser?>()
    private val listFollowerUser = MutableLiveData<ArrayList<Follower>>()
    private val listFollowingUser = MutableLiveData<ArrayList<Following>>()

    fun getDetailViewModel(username : String?, context: Context) : LiveData<DetailUser?>{

        ConfigNetwork.getUser().getDetailDataUser(username).enqueue(object : Callback<DetailUser>{
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                val body = response.body()

                val user = DetailUser(
                        name = body?.name,
                        login = body?.login,
                        avatarUrl = body?.avatarUrl,
                        followers = body?.followers,
                        following = body?.following,
                        publicRepos = body?.publicRepos,
                        location = body?.location,
                        company = body?.company,
                        twitterUsername = body?.twitterUsername,
                        bio = body?.bio
                )

                listUser.postValue(user)
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })

        return listUser

    }

    fun getFollowerViewModel(username : String?) : LiveData<ArrayList<Follower>>{

        ConfigNetwork.getUser().getFollowerUser(username).enqueue(object : Callback<ArrayList<Follower>>{
            override fun onResponse(
                call: Call<ArrayList<Follower>>,
                response: Response<ArrayList<Follower>>
            ) {
                val body = response.body()

                listFollowerUser.postValue(body)
            }

            override fun onFailure(call: Call<ArrayList<Follower>>, t: Throwable) {

            }

        })

        return listFollowerUser

    }

    fun getFollowingViewModel(username : String?) : LiveData<ArrayList<Following>>{

        ConfigNetwork.getUser().getFollowingUser(username).enqueue(object : Callback<ArrayList<Following>>{
            override fun onResponse(
                call: Call<ArrayList<Following>>,
                response: Response<ArrayList<Following>>
            ) {
                val body = response.body()

                listFollowingUser.postValue(body)
            }

            override fun onFailure(call: Call<ArrayList<Following>>, t: Throwable) {

            }

        })

        return listFollowingUser

    }

}
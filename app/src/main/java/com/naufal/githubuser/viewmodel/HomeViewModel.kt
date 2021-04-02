package com.naufal.githubuser.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naufal.githubuser.model.Article
import com.naufal.githubuser.model.ArticlesItem
import com.naufal.githubuser.model.ItemsItem
import com.naufal.githubuser.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<ItemsItem>?>()

    fun getUserHomeViewModel() : LiveData<ArrayList<ItemsItem>?>{
        ConfigNetwork.getUser().getListDataUser().enqueue(object : Callback<ArrayList<ItemsItem>>{
            override fun onResponse(call: Call<ArrayList<ItemsItem>>, response: Response<ArrayList<ItemsItem>>) {
                val body = response.body()

                listUser.postValue(body)
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
            }

        })
        return listUser
    }

}
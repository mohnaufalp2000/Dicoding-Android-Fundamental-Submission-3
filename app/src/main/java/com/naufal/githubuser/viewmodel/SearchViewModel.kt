package com.naufal.githubuser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naufal.githubuser.model.ItemsItem
import com.naufal.githubuser.model.Search
import com.naufal.githubuser.network.ConfigNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<ItemsItem>?>()

    fun setSearchViewModelUser(username: String, context: Context){
        ConfigNetwork.getUser().searchDataUser(username).enqueue(object : Callback<Search>{
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                val body = response.body()?.items

                listUser.postValue(body)
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun getSearchViewModelUser() : LiveData<ArrayList<ItemsItem>?>{
        return listUser
    }

}
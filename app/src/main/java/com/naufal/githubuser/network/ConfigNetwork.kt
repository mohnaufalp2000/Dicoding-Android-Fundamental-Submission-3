package com.naufal.githubuser.network

import com.naufal.githubuser.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConfigNetwork {

    companion object{

        const val TOKEN = BuildConfig.GITHUB_TOKEN

        fun getUser() : UserService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(UserService::class.java)
        }

    }
}
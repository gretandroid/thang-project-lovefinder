package com.eesolutions.jeux.lovefinder.webservice

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://625d72d54c36c75357753172.mockapi.io/api/"
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val userDao: UserDao by lazy {
        retrofit.create(UserDao::class.java)
    }
}
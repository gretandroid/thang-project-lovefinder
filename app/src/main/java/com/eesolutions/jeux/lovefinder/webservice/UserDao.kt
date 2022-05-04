package com.eesolutions.jeux.lovefinder.webservice


import com.eesolutions.jeux.lovefinder.model.User
import retrofit2.http.*

interface UserDao {
    @GET("users")
    suspend fun getAll() : List<User>

    @GET("users/{id}")
    suspend fun getById(@Path("id") id:Int) : User

    @POST("users")
    suspend fun create(@Body user: User) : User

    @PUT("users/{id}")
    suspend fun update(@Path("id") id: Int, @Body user: User) : User

    @DELETE("users/{id}")
    suspend fun delete(@Path("id") id: Int)
}
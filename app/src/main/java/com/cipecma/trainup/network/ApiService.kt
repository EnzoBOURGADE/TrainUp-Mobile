package com.cipecma.trainup.network

import org.w3c.dom.Text
import retrofit2.http.*
import retrofit2.http.FormUrlEncoded
import java.sql.Time

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("program/all")
    suspend fun getAllPrograms(): List<ProgramResponse>

    @GET("friends/all")
    suspend fun getAllFriends(): List<FriendsResponse>


    //Les autres appels API ICIs

}

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

data class ProgramResponse(val id: Int, val name: String, val id_user: Int, val id_cat: Int)

data class FriendsResponse(val id_user_1: Int, val id_user_2: Int)

data class ProgramItem(val id: Int, val name: String)

data class FriendsItem(val id_user_1: Int, val id_user_2: Int)
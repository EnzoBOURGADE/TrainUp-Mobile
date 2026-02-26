package com.cipecma.trainup.network

import retrofit2.http.*
import retrofit2.http.FormUrlEncoded

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

data class ProgramResponse(val id: Int, val name: String, val id_user: Int, val id_cat: Int, )
interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("program/all")
    suspend fun program(
        @Query("id") id: Int? = null,
        @Query("name") name: String? = null,
        @Query("id_user") id_user: Int? = null,
        @Query("id_cat") id_cat: Int? = null
    ): List<ProgramResponse>


    //Les autres appels API ICIs

}
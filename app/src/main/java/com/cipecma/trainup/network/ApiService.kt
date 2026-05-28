package com.cipecma.trainup.network

import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.FormUrlEncoded

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("program/user/{id}")
    suspend fun getProgramNames(@Path("id") userId: Int?): List<Program>

    @GET("users/{id}")
    suspend fun getNameById(@Path("id") userId: Int?): String

    @GET("friends/user/{id}")
    suspend fun getFriends(@Path("id") userId: Int?): List<Friends>

    @FormUrlEncoded
    @POST("program/delete")
    suspend fun deleteProgram(
        @Field("id") id: Int,
        @Field("id_user") userId: Int?
    ): Response<DeleteResponse>

    @FormUrlEncoded
    @POST("program/create")
    suspend fun createProgram(
        @Field("name") name: String,
        @Field("id_user") userId: Int?
    ): Response<CreateResponse>

    @FormUrlEncoded
    @POST("program/update/{id}")
    suspend fun updateProgram(
        @Path("id") id: Int?,
        @Field("name") name: String,
        @Field("id_user") userId: Int?,
    ): Response<UpdateResponse>


    data class Program(
        val id: Int,
        val name: String
    )

    data class Friends(
        var id_user1: Int,
        var id_user2: Int,
        var friend: String
    )

    data class FriendDisplay(

        var id_friend: Int,
        var name_friend: String

    )

    data class DeleteResponse(
        val success: Boolean,
        val message: String
    )

    data class CreateResponse(
        val success: Boolean,
        val message: String,
    )

    data class UpdateResponse(
        val success: Boolean,
        val message: String,
    )

    data class LoginRequest(val email: String, val password: String)
    data class LoginResponse(
        val token: String,
        val id_user : Int,
    )
}
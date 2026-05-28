package com.cipecma.trainup.network

import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.FormUrlEncoded
import java.util.TimeZone

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("program/user/{id}")
    suspend fun getProgramNames(@Path("id") userId: Int?): List<Program>

    @GET("category-program/all")
    suspend fun getCategoriesProgram(): List<CategoryProgram>

    @GET("users/{id}")
    suspend fun getNameById(@Path("id") userId: Int?): UserResponse

    @GET("friends/user/{id}")
    suspend fun getFriends(@Path("id") userId: Int?): List<Friends>

    @DELETE("program/delete/{id}")
    suspend fun deleteProgram(
        @Path("id") id: Int
    ): Response<DeleteResponse>

    @FormUrlEncoded
    @POST("program/save")
    suspend fun createProgram(
        @Field("name") name: String,
        @Field("id_user") userId: Int?,
        @Field("id_cat") categoryId: Int
    ): Response<CreateResponse>

    @POST("friends/delete/{id_user_1}/{id_user_2}")
    suspend fun deleteFriends(
        @Path("id_user_1") id1: Int,
        @Path("id_user_2") id2: Int
    ): Response<DeleteResponse>

    @FormUrlEncoded
    @POST("program/update/{id}")
    suspend fun updateProgram(
        @Path("id") id: Int?,
        @Field("name") name: String,
        @Field("id_user") userId: Int?,
    ): Response<UpdateResponse>


    data class Program(
        val id: Int,
        val name: String,
        val id_user: Int,
        val id_cat: Int
    )

    data class Friends(
        var id_user_1: Int,
        var id_user_2: Int
    )

    data class CategoryProgram(
        var id: Int,
        var name: String
    )

    data class User (
        var username: String,
        var email: String,
        var first_name: String,
        var last_name: String,
        var birthdate: Date,
        var created_at: Date,
        var updated_at: Date
    )

    data class Date (
        var date: String
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

    data class UserResponse(
        val user: User
    )
}
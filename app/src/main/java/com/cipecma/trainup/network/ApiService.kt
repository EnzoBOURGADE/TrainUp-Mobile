package com.cipecma.trainup.network

import org.w3c.dom.Text
import retrofit2.http.*
import retrofit2.http.FormUrlEncoded
import java.sql.Time

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

data class ProgramResponse(val id: Int, val name: String, val id_user: Int, val id_cat: Int, )

data class ExerciceResponse(
    val id: Int,
    val name: String,
    val description: Text,
    val rest_time: Int,
    val reps: Int,
    val nber_series: Int,
    val time_series: Time,
    val id_user: Int,
    val id_cat: Int,
    )

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("program/all")
    suspend fun getAllPrograms(): List<ProgramResponse>

    @GET("exercices/all")
    suspend fun getAllExercices(): List<ExerciceResponse>


    //Les autres appels API ICIs

}
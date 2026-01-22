package com.cipecma.trainup.network

import com.cipecma.trainup.auth.AuthManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASER_URL = "https://slam.cipecma.net/2426/ebourgade/trainup/api/"

    //Client sans authentification (pour le login)
    private val publicClient = OkHttpClient.Builder().build()

    private val authenticatedClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor{ chain ->
                val token = AuthManager.getToken()
                val request = if (token != null) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(request)
            }
            .build()

    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASER_URL)
        .client(authenticatedClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
    }

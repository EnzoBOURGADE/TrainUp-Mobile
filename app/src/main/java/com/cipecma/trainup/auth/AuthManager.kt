package com.cipecma.trainup.auth

object AuthManager {
    private var token: String? = null;
    private var id_user: Int = -1;

    fun setToken(newToken: String) {
        token = newToken
    }

    fun setUserId(userId: Int) {
        id_user = userId
    }

    fun getToken(): String? = token

    fun isLoggedIn(): Boolean = token != null

    fun clearToken() {
        token = null
    }

    fun getUserId(): Int = id_user
}
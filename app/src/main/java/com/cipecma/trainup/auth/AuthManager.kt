package com.cipecma.trainup.auth

object AuthManager {
    private var token: String? = null;

    fun setToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String? = token

    fun isLoggedIn(): Boolean = token != null

    fun clearToken() {
        token = null
    }
}
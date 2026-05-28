package com.cipecma.trainup.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.network.ApiService.FriendDisplay
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    suspend fun findNumberFriends(userId: Int?): Int {
        return try {
            val response = RetrofitClient.api.getFriends(userId)
            response.size

        } catch (e: Exception) {
            Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
            0
        }
    }

    suspend fun findNumberPrograms(userId: Int?): Int {
        return try {
            val response = RetrofitClient.api.getProgramNames(userId)
            response.size

        } catch (e: Exception) {
            Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
            0
        }
    }

    suspend fun findNameUser(userId: Int?): Int {
        return try {
            val response = RetrofitClient.api.getProgramNames(userId)
            response.size

        } catch (e: Exception) {
            Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
            0
        }
    }
}
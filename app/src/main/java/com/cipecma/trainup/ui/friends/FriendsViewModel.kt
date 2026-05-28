package com.cipecma.trainup.ui.friends

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.network.ApiService.FriendDisplay
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {

    private val _friends = MutableLiveData<List<FriendDisplay>>()
    val Friends: LiveData<List<FriendDisplay>> = _friends

    fun fetchFriends(userId: Int?) {
        viewModelScope.launch {
            try {
                Log.d("DEBUG_API", "CALL getFriends userId=$userId")
                val response = RetrofitClient.api.getFriends(userId)
                Log.d("DEBUG_API", "getFriends OK size=${response.size}")

                val result = mutableListOf<FriendDisplay>()

                for (friendship in response) {

                    val friendId = if (friendship.id_user_1 == AuthManager.getUserId()) {
                        friendship.id_user_2
                    } else {
                        friendship.id_user_1
                    }

                    val friend = try {
                        RetrofitClient.api.getNameById(friendId)
                    } catch (e: Exception) {
                        Log.e("DEBUG_API", "User introuvable id=$friendId", e)
                        null
                    }

                    if (friend != null) {
                        result.add(
                            FriendDisplay(
                                id_friend = friendId,
                                name_friend = friend.user.username
                            )
                        )
                    }
                }

                _friends.value = result

            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
                _friends.value = emptyList()
            }
        }
    }

    fun deleteFriends(friendId: Int, userId: Int) {
        Log.d("DEBUG_API", "DELETE CLICKED friendId=$friendId userId=$userId")
        viewModelScope.launch {
            try {
                val response1 = RetrofitClient.api.deleteFriends(userId, friendId)

                Log.d("DEBUG_API", "DELETE code=${response1.code()}")
                Log.d("DEBUG_API", "DELETE body=${response1.body()}")
                Log.d("DEBUG_API", "DELETE error=${response1.errorBody()?.string()}")

                if (!response1.isSuccessful) {
                    RetrofitClient.api.deleteFriends(friendId, userId)
                }
            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR API", e)
            }
        }
        fetchFriends(userId)
    }
}
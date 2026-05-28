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
                val response = RetrofitClient.api.getFriends(userId)



                Log.d("DEBUG_RAW", response.toString())

                Log.d("DEBUG_RAW", response.size.toString())

                val result = mutableListOf<FriendDisplay>()

                for (friendship in response) {

                    val friendId =
                        if (friendship.id_user_1 == AuthManager.getUserId()) {
                            friendship.id_user_2
                        } else {
                            friendship.id_user_1
                        }

                    val friend =
                        RetrofitClient.api.getNameById(friendId)

                    Log.d("DEBUG_APII", friend.user.username)

                    result.add(
                        FriendDisplay(
                            id_friend = friendId,
                            name_friend = friend.user.username
                        )
                    )
                }

                Log.d("DEBUG_API", result[0].name_friend)
                _friends.value = result

            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
                _friends.value = emptyList()
            }
        }
    }

    fun deleteFriends(friendsId: Int, userId: Int) {

    }
}
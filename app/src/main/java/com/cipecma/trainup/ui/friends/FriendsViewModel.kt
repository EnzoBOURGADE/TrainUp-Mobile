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

                val result = mutableListOf<FriendDisplay>()

                for (friendship in response) {

                    val friendId =
                        if (friendship.id_user1 == AuthManager.getUserId()) {
                            friendship.id_user2
                        } else {
                            friendship.id_user1
                        }

                    val friendName =
                        RetrofitClient.api.getNameById(friendId)

                    result.add(
                        FriendDisplay(
                            id_friend = friendId,
                            name_friend = friendName
                        )
                    )
                }

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
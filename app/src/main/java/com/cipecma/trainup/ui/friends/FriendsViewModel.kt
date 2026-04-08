import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.network.FriendsItem
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class FriendsViewModel : ViewModel() {
    private val _texts = MutableLiveData<List<FriendsItem>>()
    val texts: LiveData<List<FriendsItem>> = _texts
    fun loadAllFriends() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAllFriends()

                _texts.value = response.map { r ->
                    FriendsItem(
                        id_user_1 = r.id_user_1,
                        id_user_2 = r.id_user_2
                    )
                }
            } catch (e: Exception) {
                Log.e("PROGRAM", "Erreur API: ${e.message}")
                _texts.value = emptyList()
            }
        }
    }
}
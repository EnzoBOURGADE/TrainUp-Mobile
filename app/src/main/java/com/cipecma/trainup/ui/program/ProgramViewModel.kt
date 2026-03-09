import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.network.RetrofitClient
import com.cipecma.trainup.ui.program.ProgramItem
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel() {
    private val _texts = MutableLiveData<List<ProgramItem>>()
    val texts: LiveData<List<ProgramItem>> = _texts
    fun loadAllPrograms() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAllPrograms()

                _texts.value = response.map { r ->
                    ProgramItem(
                        id = r.id,
                        name = r.name,
                        id_user = r.id_user,
                        id_cat = r.id_cat
                    )
                }
            } catch (e: Exception) {
                Log.e("PROGRAM", "Erreur API: ${e.message}")
                _texts.value = emptyList()
            }
        }
    }
}
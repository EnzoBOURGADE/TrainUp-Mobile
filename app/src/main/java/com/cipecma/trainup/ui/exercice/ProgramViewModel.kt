import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.network.RetrofitClient
import com.cipecma.trainup.ui.exercice.ExerciceItem
import com.cipecma.trainup.ui.program.ProgramItem
import kotlinx.coroutines.launch

class ExerciceViewModel : ViewModel() {
    private val _texts = MutableLiveData<List<ExerciceItem>>()
    val texts: LiveData<List<ExerciceItem>> = _texts
    fun loadAllExercices() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAllExercices()

                _texts.value = response.map { r ->
                    ExerciceItem(
                        id = r.id,
                        name = r.name,
                        description = r.description,
                        rest_time = r.rest_time,
                        reps = r.reps,
                        nber_series = r.nber_series,
                        time_series = r.time_series,
                        id_cat = r.id_cat,
                        id_muscle = r.id_user
                    )
                }
            } catch (e: Exception) {
                Log.e("EXERCICE", "Erreur API: ${e.message}")
                _texts.value = emptyList()
            }
        }
    }
}
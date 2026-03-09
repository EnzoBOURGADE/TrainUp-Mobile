package com.cipecma.trainup.ui.program

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.network.ProgramResponse
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

data class ProgramItem(
    val id: Int,
    val name: String,
    val id_user: Int?,
    val id_cat: Int?
)

class ProgramViewModel : ViewModel() {

    private val _texts = MutableLiveData<List<ProgramItem>>()
    val texts: LiveData<List<ProgramItem>> = _texts

    fun loadPrograms(id: Int? = null, name: String? = null, id_user: Int? = null, id_cat: Int? = null) {
        viewModelScope.launch {
            try {
                val response: List<ProgramResponse> = RetrofitClient.api.program(id, name, id_user, id_cat)
                Log.i("PROGRAM", "API response size=${response.size}")

                // Conversion ProgramResponse -> ProgramItem
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
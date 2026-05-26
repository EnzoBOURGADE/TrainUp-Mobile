package com.cipecma.trainup.ui.program

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipecma.trainup.network.ApiService.Program
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class ProgramViewModel : ViewModel() {
    private val _programNames = MutableLiveData<List<Program>>()
    val programNames: LiveData<List<Program>> = _programNames


    fun fetchPrograms(userId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getProgramNames(userId)

                Log.d("DEBUG_API", "Succès ! Nombre de programmes : ${response.size}")

                if (response.isNotEmpty()) {
                    Log.d("DEBUG_API", "Premier programme : ${response[0].name}")
                }

                _programNames.value = response
            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR LORS DE L'APPEL", e)
                _programNames.value = emptyList()
            }
        }
    }

    fun deleteProgram(programId: Int?, userId: Int?) {

    }
}
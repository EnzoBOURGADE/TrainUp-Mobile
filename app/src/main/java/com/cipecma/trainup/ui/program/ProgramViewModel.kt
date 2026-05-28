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
                Log.d("DEBUG_API", "Appel API avec userId = $userId")
                val response = RetrofitClient.api.getProgramNames(userId)
                Log.d("DEBUG_API", "Réponse brute = $response")
                Log.d("DEBUG_API", "Taille liste = ${response.size}")
                response.forEach {
                    Log.d("DEBUG_API", "Programme -> id=${it.id} name=${it.name}")
                }
                _programNames.value = response

            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR API", e)
                _programNames.value = emptyList()
            }
        }
    }

    fun deleteProgram(programId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.deleteProgram(programId)

                Log.d("DEBUG_API", "code = ${response.code()}")
                Log.d("DEBUG_API", "body = ${response.errorBody()?.string()}")

                fetchPrograms(userId)

            } catch (e: Exception) {
                Log.e("DEBUG_API", "ERREUR API", e)
            }
        }
    }
}
package com.cipecma.trainup.ui.program

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cipecma.trainup.MainActivity
import com.cipecma.trainup.R
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.http.Query

class ProgramActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_program)

        program(id = null, name = null, id_user = null, id_cat = null)
    }

    private fun program(id: Int?, name: String?, id_user: Int?, id_cat: Int?) {
        Log.i("PROGRAM", "program() appelée") // <-- vérifie si cette ligne apparaît
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.program(id, name, id_user, id_cat)
                Log.i("PROGRAM", "Réponse reçue, taille=${response.size}") // <-- pour tester
                response.forEach { program ->
                    Log.i("PROGRAM", "id=${program.id}, name=${program.name}, id_user=${program.id_user}, id_cat=${program.id_cat}")
                }
            } catch (e: HttpException) {
                Log.e("PROGRAM", "Erreur HTTP: ${e.code()}")
            } catch (e: Exception) {
                Log.e("PROGRAM", "Erreur: ${e.message}")
            }
        }

        /*lifecycleScope.launch {
            try {
                //Appel de notre fonction pour l'API
                val response = RetrofitClient.api.login(email, password)

                //Stocker le jeton
                AuthManager.setToken(response.token)

                //Sauvegarder le jeton de manière persistante (DISQUE DUR)
                getSharedPreferences("auth", MODE_PRIVATE)
                    .edit {
                        putString("token", response.token)
                    }



                Toast.makeText(
                    this@ProgramActivity,
                    "Connexion réussie !",
                    Toast.LENGTH_SHORT
                ).show()

                goToMainActivity()
            }
            catch(e: HttpException) {
                //Gestion des erreur HTTP
                when (e.code()) {
                    401 -> {
                        Toast.makeText(
                            this@ProgramActivity,
                            "Email ou mot de passe incorrect",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            this@ProgramActivity,
                            "Erreur serveur: ${e.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@ProgramActivity,
                    "Erreur de connexion: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }*/
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() //Empêcher de revenir au login avec le bouton retour
    }
}
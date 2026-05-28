package com.cipecma.trainup.ui.profil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.FragmentProfilBinding
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        loadProfile()

        return binding.root
    }

    private fun loadProfile() {

        val currentUserId: Int = AuthManager.getUserId()

        lifecycleScope.launch {

            try {

                val userResponse = RetrofitClient.api.getNameById(currentUserId)

                val user = userResponse.user

                val inputFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss.SSSSSS"
                )

                val birthdateFormatter = DateTimeFormatter.ofPattern(
                    "dd MMMM yyyy",
                    Locale.FRENCH
                )

                val dateTimeFormatter = DateTimeFormatter.ofPattern(
                    "dd MMMM yyyy HH:mm",
                    Locale.FRENCH
                )

                val birthdate = LocalDateTime.parse(
                    user.birthdate.date,
                    inputFormatter
                )

                val createdAt = LocalDateTime.parse(
                    user.created_at.date,
                    inputFormatter
                )

                val updatedAt = LocalDateTime.parse(
                    user.updated_at.date,
                    inputFormatter
                )

                binding.tvUsername.text = user.username
                binding.tvEmail.text = user.email

                binding.tvFirstname.text = user.first_name
                binding.tvLastname.text = user.last_name

                binding.tvBirthdate.text =
                    birthdate.format(birthdateFormatter)
                        .replaceFirstChar { it.uppercase() }

                binding.tvCreatedAt.text =
                    createdAt.format(dateTimeFormatter)
                        .replaceFirstChar { it.uppercase() }

                binding.tvUpdatedAt.text =
                    updatedAt.format(dateTimeFormatter)
                        .replaceFirstChar { it.uppercase() }

                val programs = RetrofitClient.api.getProgramNames(currentUserId)

                binding.tvProgramsCount.text = programs.size.toString()

                val friends = RetrofitClient.api.getFriends(currentUserId)

                binding.tvFriendsCount.text = friends.size.toString()

            } catch (e: Exception) {

                Log.e("PROFILE_FRAGMENT", "Erreur chargement profil", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
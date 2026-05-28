package com.cipecma.trainup.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cipecma.trainup.R
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.FragmentHomeBinding
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnSeeFriends.setOnClickListener {

            requireActivity()
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view)
                .selectedItemId = R.id.nav_friends
        }

        binding.btnSeePrograms.setOnClickListener {

            requireActivity()
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav_view)
                .selectedItemId = R.id.nav_program
        }

        loadHomeData()

        return binding.root
    }

    private fun loadHomeData() {

        val currentUserId: Int = AuthManager.getUserId()

        lifecycleScope.launch {

            try {

                val userResponse = RetrofitClient.api.getNameById(currentUserId)
                val username = userResponse.user.username

                binding.tvHomeTitle.text = "Bonjour $username ✌️"

                val programs = RetrofitClient.api.getProgramNames(currentUserId)

                binding.tvProgramsCount.text = programs.size.toString()

                val friends = RetrofitClient.api.getFriends(currentUserId)

                binding.tvFriendsCount.text = friends.size.toString()

            } catch (e: Exception) {

                Log.e("HOME_FRAGMENT", "Erreur chargement home", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
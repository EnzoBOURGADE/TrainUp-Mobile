package com.cipecma.trainup.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cipecma.trainup.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy {
        requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadStates()
        setupListeners()
    }

    private fun loadStates() {

        binding.switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        binding.switchFriendsNotif.isChecked = prefs.getBoolean("friends_notif", true)
        binding.switchProgramNotif.isChecked = prefs.getBoolean("program_notif", true)
        binding.switchDarkMode.isChecked = prefs.getBoolean("dark_mode", false)
        binding.switchAnimations.isChecked = prefs.getBoolean("animations", true)
        binding.switchProfilePublic.isChecked = prefs.getBoolean("profile_public", true)
        binding.switchFriendRequests.isChecked = prefs.getBoolean("friend_requests", true)
        binding.switchAutoLogin.isChecked = prefs.getBoolean("auto_login", true)
    }

    private fun setupListeners() {

        binding.switchNotifications.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("notifications", v).apply()
        }

        binding.switchFriendsNotif.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("friends_notif", v).apply()
        }

        binding.switchProgramNotif.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("program_notif", v).apply()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("dark_mode", v).apply()
        }

        binding.switchAnimations.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("animations", v).apply()
        }

        binding.switchProfilePublic.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("profile_public", v).apply()
        }

        binding.switchFriendRequests.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("friend_requests", v).apply()
        }

        binding.switchAutoLogin.setOnCheckedChangeListener { _, v ->
            prefs.edit().putBoolean("auto_login", v).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
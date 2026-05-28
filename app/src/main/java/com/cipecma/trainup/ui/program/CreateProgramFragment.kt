package com.cipecma.trainup.ui.program

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.FragmentCreateProgramBinding
import com.cipecma.trainup.network.ApiService
import com.cipecma.trainup.network.RetrofitClient
import kotlinx.coroutines.launch

class CreateProgramFragment : Fragment() {

    private var _binding: FragmentCreateProgramBinding? = null
    private val binding get() = _binding!!

    private var categories: List<ApiService.CategoryProgram> = emptyList()
    private var selectedCategoryId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateProgramBinding.inflate(inflater, container, false)

        loadCategories()
        setupButton()

        return binding.root
    }

    private fun loadCategories() {

        lifecycleScope.launch {

            try {

                categories = RetrofitClient.api.getCategoriesProgram()

                val categoryNames = categories.map { it.name }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    categoryNames
                )

                binding.dropdownCategory.setAdapter(adapter)

                binding.dropdownCategory.setOnItemClickListener { _, _, position, _ ->
                    selectedCategoryId = categories[position].id
                }

            } catch (e: Exception) {

                Log.e("DEBUG_API", "Erreur catégories", e)

                Toast.makeText(
                    requireContext(),
                    "Erreur chargement catégories",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupButton() {

        binding.btnCreateProgram.setOnClickListener {

            val programName =
                binding.editProgramName.text.toString().trim()

            if (programName.isEmpty()) {

                binding.editProgramName.error =
                    "Nom obligatoire"

                return@setOnClickListener
            }

            if (selectedCategoryId == 0) {

                Toast.makeText(
                    requireContext(),
                    "Sélectionne une catégorie",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            createProgram(
                programName,
                selectedCategoryId
            )
        }
    }

    private fun createProgram(
        name: String,
        categoryId: Int
    ) {

        lifecycleScope.launch {

            try {

                Log.d("DEBUG_API", "CREATE PROGRAM")
                Log.d("DEBUG_API", "name = $name")
                Log.d("DEBUG_API", "user = ${AuthManager.getUserId()}")
                Log.d("DEBUG_API", "category = $categoryId")

                val response =
                    RetrofitClient.api.createProgram(
                        name,
                        AuthManager.getUserId(),
                        categoryId
                    )

                Log.d("DEBUG_API", "code = ${response.code()}")
                Log.d("DEBUG_API", "body = ${response.body()}")
                Log.d("DEBUG_API", "error = ${response.errorBody()?.string()}")

                if (response.isSuccessful) {

                    Toast.makeText(
                        requireContext(),
                        "Programme créé",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().popBackStack()

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Erreur création",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {

                Log.e("DEBUG_API", "ERREUR API", e)

                Toast.makeText(
                    requireContext(),
                    "Erreur API",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
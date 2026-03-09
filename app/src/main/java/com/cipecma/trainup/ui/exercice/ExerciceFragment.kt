package com.cipecma.trainup.ui.exercice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.cipecma.trainup.databinding.ItemTransformBinding

class ExerciceFragment : Fragment() {

    private var _binding: FragmentExerciceBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ExerciceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciceBinding.inflate(inflater, container, false)
        val root = binding.root

        val viewModel = ViewModelProvider(this).get(ExerciceViewModel::class.java)

        adapter = ExerciceAdapter()
        binding.recyclerviewExercice.adapter = adapter

        viewModel.texts.observe(viewLifecycleOwner) { exercices ->
            adapter.submitList(exercices)
        }

        viewModel.loadAllExercices()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ExerciceAdapter :
        ListAdapter<ExerciceItem, ExerciceViewHolder>(object : DiffUtil.ItemCallback<ExerciceItem>() {
            override fun areItemsTheSame(oldItem: ExerciceItem, newItem: ExerciceItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ExerciceItem, newItem: ExerciceItem): Boolean =
                oldItem == newItem
        }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciceViewHolder {
            val binding = ItemTransformBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ExerciceViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ExerciceViewHolder, position: Int) {
            val item = getItem(position)
            holder.textView.text = item.toString()
        }
    }

    class ExerciceViewHolder(binding: ItemTransformBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textViewItemTransform
    }
}
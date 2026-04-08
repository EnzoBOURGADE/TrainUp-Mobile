package com.cipecma.trainup.ui.program

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
import com.cipecma.trainup.network.ProgramItem
import com.cipecma.trainup.databinding.FragmentProgramBinding
import com.cipecma.trainup.databinding.ItemTransformBinding

class ProgramFragment : Fragment() {

    private var _binding: FragmentProgramBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProgramAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgramBinding.inflate(inflater, container, false)
        val root = binding.root

        val viewModel = ViewModelProvider(this).get(ProgramViewModel::class.java)

        adapter = ProgramAdapter()
        binding.recyclerviewProgram.adapter = adapter

        viewModel.texts.observe(viewLifecycleOwner) { programs ->
            adapter.submitList(programs)
        }

        viewModel.loadAllPrograms()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ProgramAdapter :
        ListAdapter<ProgramItem, ProgramViewHolder>(object : DiffUtil.ItemCallback<ProgramItem>() {
            override fun areItemsTheSame(oldItem: ProgramItem, newItem: ProgramItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ProgramItem, newItem: ProgramItem): Boolean =
                oldItem == newItem
        }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
            val binding = ItemTransformBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProgramViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
            val item = getItem(position)
            holder.textView.text = item.toString()
        }
    }

    class ProgramViewHolder(binding: ItemTransformBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textViewItemTransform
    }
}
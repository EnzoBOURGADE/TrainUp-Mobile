package com.cipecma.trainup.ui.friends

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
import com.cipecma.trainup.databinding.FragmentFriendsBinding
import com.cipecma.trainup.databinding.ItemTransformBinding

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root = binding.root

        val viewModel = ViewModelProvider(this).get(FriendsViewModel::class.java)

        adapter = FriendsAdapter()
        binding.recyclerViewFriends.adapter = adapter

        viewModel.texts.observe(viewLifecycleOwner) { friends ->
            adapter.submitList(friends)
        }

        viewModel.loadAllFriends()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class FriendsAdapter :
        ListAdapter<FriendsItem, FriendsViewHolder>(object : DiffUtil.ItemCallback<FriendsItem>() {
            override fun areItemsTheSame(oldItem: FriendsItem, newItem: FriendsItem): Boolean =
                (oldItem.id_user_1 == newItem.id_user_1) and (oldItem.id_user_2 == newItem.id_user_2)

            override fun areContentsTheSame(oldItem: FriendsItem, newItem: FriendsItem): Boolean =
                oldItem == newItem
        }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
            val binding = ItemTransformBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FriendsViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
            val item = getItem(position)
            holder.textView.text = item.toString()
        }
    }

    class FriendsViewHolder(binding: ItemTransformBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView: TextView = binding.textViewItemTransform
    }
}
package com.cipecma.trainup.ui.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.cipecma.trainup.R
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.FragmentFriendsBinding
import com.cipecma.trainup.network.ApiService

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private val friendsViewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerViewFriends

        val currentUserId: Int = AuthManager.getUserId()
        if (currentUserId != -1) {
            friendsViewModel.fetchFriends(userId = currentUserId)
        }

        friendsViewModel.Friends.observe(viewLifecycleOwner) { friends ->

            if (friends.isNotEmpty()) {

                val adapter = FriendsAdapter(
                    friends,
                    onClick = { friend ->

                        androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Supprimer l'ami")
                            .setMessage(
                                "Voulez-vous vraiment supprimer ${friend.name_friend} de vos amis ?"
                            )
                            .setPositiveButton("Supprimer") { _, _ ->

                                friendsViewModel.deleteFriends(
                                    friend.id_friend,
                                    currentUserId
                                )

                                android.widget.Toast.makeText(
                                    requireContext(),
                                    "Ami supprimé",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                            .setNegativeButton("Annuler", null)
                            .show()
                    }
                )

                recyclerView.adapter = adapter

            } else {

                Log.d(
                    "PROGRAM_STATUS",
                    "La liste est vide ou l'API n'a pas répondu."
                )
            }
        }

        friendsViewModel.fetchFriends(currentUserId)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class FriendsAdapter(
        private val friends: List<ApiService.FriendDisplay>,
        private val onClick: (ApiService.FriendDisplay) -> Unit,
        ) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.textView)
            val btnDelete: ImageButton = view.findViewById(R.id.btn_delete)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_friends, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val friend = friends[position]
            holder.text.text = friend.name_friend

            holder.btnDelete.setOnClickListener {
                onClick(friend)
            }
        }

        override fun getItemCount() = friends.size
    }
}
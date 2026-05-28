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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cipecma.trainup.R
import com.cipecma.trainup.auth.AuthManager
import com.cipecma.trainup.databinding.FragmentFriendsBinding
import com.cipecma.trainup.network.ApiService.Friends
import com.cipecma.trainup.network.ApiService.FriendDisplay
import com.cipecma.trainup.ui.friends.FriendsViewModel

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

        Log.d("DEBUG_USER", "User ID récupéré = $currentUserId")
        if (currentUserId != -1) {
            Log.d("DEBUG_FLOW", "Avant fetchFriends")
            friendsViewModel.fetchFriends(userId = currentUserId)
            Log.d("DEBUG_FLOW", "Apres fetchFriends")
        }

        friendsViewModel.Friends.observe(viewLifecycleOwner) { friends ->
            Log.d("DEBUG_PROGRAM", "Observer déclenché")

            Log.d("DEBUG_PROGRAM", "Nombre amitiés = ${friends.size}")

            friends.forEach {

                Log.d("DEBUG_PROGRAM", "Amitié = ${it.name_friend}")

            }


            if (friends.isNotEmpty()) {
                val adapter = FriendsAdapter(friends, onClick = { friend ->
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Supprimer l'amitié")
                        .setMessage("Voulez-vous vraiment supprimer ${friend.name_friend} ?")
                        .setPositiveButton("Supprimer") { _, _ ->
                            friendsViewModel.deleteFriends(friend.id_friend, currentUserId)
                            android.widget.Toast.makeText(requireContext(), "amitié supprimée", android.widget.Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Annuler", null)
                        .show()
                }
                )
                recyclerView.adapter = adapter
            } else {
                Log.d("FRIENDS_STATUS", "La liste est vide ou l'API n'a pas répondu.")
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class FriendsAdapter(
        private val friends: List<FriendDisplay>,
        private val onClick: (FriendDisplay) -> Unit,
    ) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.friend_name)
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
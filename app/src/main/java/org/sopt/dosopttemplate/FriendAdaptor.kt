package org.sopt.dosopttemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemFriendBinding

class FriendAdaptor(context: Context) : RecyclerView.Adapter<FriendViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var friendList: List<Friend> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.onBind(friendList[position])
    }

    override fun getItemCount(): Int = friendList.size

    fun setFriendList(friends: List<Friend>) {
        this.friendList = friends.toList()
        notifyDataSetChanged()
    }

}

class FriendViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(friend: Friend) {
        binding.ivItemImage.setImageResource(friend.profileImage)
        binding.tvItemName.text = friend.name
        binding.tvItemMessage.text = friend.self_description
    }
}
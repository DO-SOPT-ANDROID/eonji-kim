package org.sopt.dosopttemplate.data.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.data.Friend
import org.sopt.dosopttemplate.databinding.ItemFriendBinding
import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDataDto

class UserAdaptor(context: Context) : RecyclerView.Adapter<UserViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var userList: List<ResponseUserListDataDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    fun setUserList(user: List<ResponseUserListDataDto>) {
        this.userList = user
        notifyDataSetChanged()
    }

}

class UserViewHolder(private val binding: ItemFriendBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(user: ResponseUserListDataDto) {
        // binding.ivItemImage.setImageResource(user.avatar)
        binding.tvItemName.text = user.first_name
        binding.tvItemMessage.text = user.email
    }
}
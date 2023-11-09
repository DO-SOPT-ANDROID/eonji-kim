package org.sopt.dosopttemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemMyBinding

class MyAdaptor(context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    private var myList: List<My> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMyBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    fun setMyList(my: List<My>) {
        this.myList = my.toList()
        notifyDataSetChanged()
    }
}

class MyViewHolder(private val binding: ItemMyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(my: My) {
        binding.ivItemMyImage.setImageResource(my.profileImage)
        binding.tvItemMyName.text = my.name
        binding.tvItemMyMessage.text = my.selfDescription
    }
}
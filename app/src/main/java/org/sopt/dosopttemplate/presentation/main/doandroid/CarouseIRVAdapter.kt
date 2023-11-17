package org.sopt.dosopttemplate.presentation.main.doandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.dosopttemplate.databinding.ItemPartBinding

class CarouseIRVAdapter(private val carouselDataList: ArrayList<String>) :
    RecyclerView.Adapter<CarouseIRVAdapter.CarouselItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    var onItemClickListener: OnItemClickListener? = null

    class CarouselItemViewHolder(val binding: ItemPartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPartBinding.inflate(inflater, parent, false)
        return CarouselItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val textView = holder.binding.tvPart
        textView.clipToOutline = true
        textView.text = carouselDataList[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(carouselDataList[position])
        }
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }
}
package com.example.navigationdrawerdemo.ui.devices.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.navigationdrawerdemo.R
import com.example.navigationdrawerdemo.ui.devices.DeviceUiModel


class DevicesAdapter(val onItemClick: (DeviceUiModel) -> Unit) :
    RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder>() {

    inner class DevicesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImage = itemView.findViewById<ImageView>(R.id.ivImage);
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle);
        var tvPrice = itemView.findViewById<TextView>(R.id.tvPrice);
        var tvType = itemView.findViewById<TextView>(R.id.tvType);

        init {
            itemView.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(differ.currentList[position])
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<DeviceUiModel>() {
        override fun areItemsTheSame(oldItem: DeviceUiModel, newItem: DeviceUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DeviceUiModel, newItem: DeviceUiModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<DeviceUiModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        return DevicesViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.device_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {

        val item = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(2)))
            .into(holder.ivImage)

        holder.tvTitle.text = item.title
        (item.price.toString() + " " + item.currency).also { holder.tvPrice.text = it }
        holder.tvType.text = item.type
    }
}
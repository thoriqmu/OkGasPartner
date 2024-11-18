package com.kewirausahaan.okgaspartner.ui.move

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kewirausahaan.okgaspartner.databinding.ItemMoveBinding

class OrderMoveAdapter : ListAdapter<OrderMove, OrderMoveAdapter.OrderMoveViewHolder>(DiffCallback) {

    class OrderMoveViewHolder(private var binding: ItemMoveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderData: OrderMove) {
            binding.detailMoveCreated.text = orderData.created
            binding.detailMoveDate.text = orderData.date
            binding.detailMoveLocationStart.text = orderData.location_now
            binding.detailMoveLocationEnd.text = orderData.location_destination
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderMoveViewHolder {
        return OrderMoveViewHolder(
            ItemMoveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderMoveViewHolder, position: Int) {
        val orderData = getItem(position)
        holder.bind(orderData)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderMove>() {
        override fun areItemsTheSame(oldItem: OrderMove, newItem: OrderMove): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderMove, newItem: OrderMove): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
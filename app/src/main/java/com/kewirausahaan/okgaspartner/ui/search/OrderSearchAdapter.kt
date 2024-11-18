package com.kewirausahaan.okgaspartner.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kewirausahaan.okgaspartner.databinding.ItemSearchBinding

class OrderSearchAdapter : ListAdapter<OrderSearch, OrderSearchAdapter.OrderSearchViewHolder>(DiffCallback) {

    class OrderSearchViewHolder(private var binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderData: OrderSearch) {
            binding.detailOrderCreated.text = orderData.created
            binding.detailOrderDate.text = orderData.date
            binding.detailOrderKostName.text = orderData.kost_name
            binding.detailOrderKostLocation.text = orderData.kost_location
            // ... (set data lainnya) ...
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSearchViewHolder {
        return OrderSearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderSearchViewHolder, position: Int) {
        val orderData = getItem(position)
        holder.bind(orderData)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderSearch>() {
        override fun areItemsTheSame(oldItem: OrderSearch, newItem: OrderSearch): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderSearch, newItem: OrderSearch): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
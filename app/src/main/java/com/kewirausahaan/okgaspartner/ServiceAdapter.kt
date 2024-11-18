package com.kewirausahaan.okgaspartner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kewirausahaan.okgaspartner.databinding.ItemServiceBinding

class ServiceAdapter(private val serviceList: List<ServiceItem>) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serviceItem: ServiceItem) {
            binding.serviceImage.setImageResource(serviceItem.image)
            binding.serviceText.text = serviceItem.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(serviceList[position])
    }

    override fun getItemCount(): Int = serviceList.size
}
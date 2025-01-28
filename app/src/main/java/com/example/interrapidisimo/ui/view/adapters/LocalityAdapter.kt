package com.example.interrapidisimo.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.databinding.LocalityItemBinding

class LocalityAdapter(
    private var data: List<ResponseDataLocalityDTO>
) : RecyclerView.Adapter<LocalityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LocalityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: LocalityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResponseDataLocalityDTO) {
            binding.tittleTableTextview.text = data.abbreviation
            binding.dataOneTextview.text = data.abbreviation
            binding.dataTwoTextview.text = data.city
        }

    }

}
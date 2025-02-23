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
        holder.bind(data[position], position == data.size - 1)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: LocalityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResponseDataLocalityDTO, isLast: Boolean) {
            binding.tittleTableTextview.text = data.name
            binding.dataOneTextview.text = data.abbreviation
            binding.dataTwoTextview.text = data.fullName
            binding.dataThreeTextview.text = data.postal
            binding.dataFourTextview.text = if (data.amount == null) "0.0" else data.amount.toString()

            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = if (isLast) 50 else 0
            binding.root.layoutParams = layoutParams
        }

    }

}
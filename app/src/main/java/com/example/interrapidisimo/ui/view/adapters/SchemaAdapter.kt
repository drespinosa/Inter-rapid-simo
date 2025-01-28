package com.example.interrapidisimo.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.databinding.SchemaItemBinding

class SchemaAdapter(
    private var data: List<ResponseDataSchemeDTO>
) : RecyclerView.Adapter<SchemaAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SchemaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: SchemaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ResponseDataSchemeDTO) {
            binding.tittleTableTextview.text = data.name
            binding.dataOneTextview.text = data.content
        }
    }

}
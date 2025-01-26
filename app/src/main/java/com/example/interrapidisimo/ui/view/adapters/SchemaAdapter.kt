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
            binding.dataOneTextview.text = data.primaryKey
            binding.dataTwoTextview.text = data.size
            binding.dataThreeTextview.text = data.field
            binding.dataFourTextview.text = data.updateDate//formatterDate(data.updateDate)
        }

        /**
         * Formatea una cadena de fecha eliminando los milisegundos y cambiando su formato a "dd MMMM yyyy HH:mm:ss" en español
         */
        /*private fun formatterDate(date: String): String{
            val withoutMilliseconds = date.substring(0, date.indexOf('.'))
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateFormatter = LocalDateTime.parse(withoutMilliseconds, formatter)
            val newFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss", Locale("es", "ES"))
            return dateFormatter.format(newFormatter)
        }*/
    }

}
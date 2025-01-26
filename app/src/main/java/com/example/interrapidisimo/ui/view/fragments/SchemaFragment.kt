package com.example.interrapidisimo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interrapidisimo.databinding.FragmentSchemaBinding
import com.example.interrapidisimo.ui.view.adapters.SchemaAdapter
import com.example.interrapidisimo.ui.viewmodel.SchemaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchemaFragment : Fragment() {

    private var _binding: FragmentSchemaBinding? = null
    private val binding get() = _binding!!
    private val schemaViewModel by viewModels<SchemaViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSchemaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        schemaViewModel.getSchemas()
        observers()
    }

    private fun observers(){
        schemaViewModel.successMessage.observe(viewLifecycleOwner){
            if (it.isSuccessful){
                binding.tableRecyclerView.adapter = SchemaAdapter(it.body() ?: listOf())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

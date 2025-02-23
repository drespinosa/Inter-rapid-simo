package com.example.interrapidisimo.ui.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.interrapidisimo.R
import com.example.interrapidisimo.databinding.FragmentSchemaBinding
import com.example.interrapidisimo.ui.view.adapters.SchemaAdapter
import com.example.interrapidisimo.ui.viewmodel.SchemaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchemaFragment : Fragment() {

    private var _binding: FragmentSchemaBinding? = null
    private val binding get() = _binding!!
    private val schemaViewModel by viewModels<SchemaViewModel>()
    private var loadingDialog: AlertDialog? = null

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
            if (it.isSuccessful) {
                binding.tableRecyclerView.isVisible = true
                binding.imageError.isVisible = false
                binding.tableRecyclerView.adapter = SchemaAdapter(it.body() ?: listOf())
            }
        }
        schemaViewModel.showOrHideLoader.observe(viewLifecycleOwner){ show ->
            showLoading(show)
        }
        schemaViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tableRecyclerView.isVisible = false
                binding.imageError.isVisible = true
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle((R.string.error))
                    .setMessage(it)
                    .setPositiveButton((R.string.retry)) { dialog, _ ->
                        dialog.dismiss()
                        schemaViewModel.getSchemas()
                    }
                    .create()

                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)
                dialog.show()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                loadingDialog = AlertDialog.Builder(requireContext())
                    .setView(R.layout.dialog_loading)
                    .setCancelable(false)
                    .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

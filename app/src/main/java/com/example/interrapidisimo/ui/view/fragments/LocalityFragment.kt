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
import com.example.interrapidisimo.databinding.FragmentLocalitiesBinding
import com.example.interrapidisimo.ui.view.adapters.LocalityAdapter
import com.example.interrapidisimo.ui.viewmodel.LocalityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalityFragment : Fragment() {

    private var _binding: FragmentLocalitiesBinding? = null
    private val binding get() = _binding!!
    private val localityViewModel by viewModels<LocalityViewModel>()
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalitiesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localityViewModel.getLocalities()
        observers()
    }

    /**
     * Configura los observadores del ViewModel.
     * Este método observa los cambios en los LiveData del ViewModel y actualiza la UI en consecuencia.
     */
    private fun observers() {
        localityViewModel.successMessage.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                binding.localityRecyclerView.isVisible = true
                binding.imageError.isVisible = false
                binding.localityRecyclerView.adapter = LocalityAdapter(it.body() ?: listOf())
            }
        }
        localityViewModel.showOrHideLoader.observe(viewLifecycleOwner){ show ->
            showLoading(show)
        }
        localityViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.localityRecyclerView.isVisible = false
                binding.imageError.isVisible = true
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle((R.string.error))
                    .setMessage(it)
                    .setPositiveButton((R.string.retry)) { dialog, _ ->
                        dialog.dismiss()
                        localityViewModel.getLocalities()
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

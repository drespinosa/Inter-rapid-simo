package com.example.interrapidisimo.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.databinding.FragmentHomeBinding
import com.example.interrapidisimo.ui.view.FragmentEventListener
import com.example.interrapidisimo.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var listener: FragmentEventListener? = null
    private val loginViewModel: LoginViewModel by activityViewModels()
    private var dataUser: UserVO? = null
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        navController = findNavController()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObservers()
        addButtonsListeners()
        listener?.onDisableButton(true)
    }

    private fun addButtonsListeners() {
        binding.localityButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_locality)
        }

        binding.schemaButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_home_to_fragment_schema)
        }
    }

    private fun addObservers() {
        loginViewModel.dataUser.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                dataUser = data
                printUserData()
            }
        }
    }

    /**
     * Muestra los datos del usuario en la UI.
     * Este método actualiza los TextView con la información del usuario.
     */
    private fun printUserData() {
        binding.userId.text = getString(R.string.identification_display, getString(R.string.identification), dataUser?.identification)
        binding.userName.text = getString(R.string.identification_display, getString(R.string.name), dataUser?.name)
        binding.nickname.text = getString(R.string.identification_display, getString(R.string.user), dataUser?.user)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listener?.onDisableButton(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentEventListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}

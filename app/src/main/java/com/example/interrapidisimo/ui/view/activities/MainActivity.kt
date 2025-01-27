package com.example.interrapidisimo.ui.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.databinding.ActivityMainBinding
import com.example.interrapidisimo.ui.view.FragmentEventListener
import com.example.interrapidisimo.ui.view.fragments.HomeFragment
import com.example.interrapidisimo.ui.viewmodel.AViewModel
import com.example.interrapidisimo.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentEventListener {

    private lateinit var binding: ActivityMainBinding
    private val aViewModel: AViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private var loadingDialog: AlertDialog? = null
    private var dataUser: UserVO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHeaderButtons()
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        loginViewModel.getUser()
        aViewModel.getQuotes()
        addObservers()
        addButtonsListeners()
    }

    private fun setupHeaderButtons() {
        binding.buttonBack.setOnClickListener {
            goToHome()
        }

        binding.buttonLogout.setOnClickListener {
            loginViewModel.deleteUser()
            logout()
        }
    }

    private fun goToHome() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun addButtonsListeners() {
        binding.button.setOnClickListener {
            aViewModel.randomModel()
        }
    }

    private fun addObservers() {
        aViewModel.model.observe(this) { model ->
            Log.d("http ${this::class.java.simpleName}", "MA observes model: ${model}")
            binding.text1.text = model.name
        }
        aViewModel.showOrHideLoader.observe(this) { isLoading ->
            Log.d("http ${this::class.java.simpleName}", "MA observes isLoading: $isLoading")
            showOrHideLoader(isLoading)
        }

        loginViewModel.dataUser.observe(this) { data ->
            dataUser = data
            Log.d("http ${this::class.java.simpleName}", "MA dataUser: $dataUser")
        }
    }

    private fun showOrHideLoader(isLoading: Boolean) {
        if (isLoading) {
            if (loadingDialog == null) {
                val builder = AlertDialog.Builder(this)
                builder.setView(layoutInflater.inflate(R.layout.dialog_loading, null))
                builder.setCancelable(false)
                loadingDialog = builder.create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDisableButton(shouldDisable: Boolean) {
        if (shouldDisable) {
            binding.buttonBack.visibility = View.GONE
        } else {
            binding.buttonBack.visibility = View.VISIBLE
        }
    }

}

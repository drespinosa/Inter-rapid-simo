package com.example.interrapidisimo.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.databinding.ActivityMainBinding
import com.example.interrapidisimo.ui.view.FragmentEventListener
import com.example.interrapidisimo.ui.view.fragments.HomeFragment
import com.example.interrapidisimo.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentEventListener {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: LoginViewModel by viewModels()
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
        addObservers()
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

    private fun addObservers() {
        loginViewModel.dataUser.observe(this) { data ->
            dataUser = data
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

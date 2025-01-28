package com.example.interrapidisimo.ui.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.BuildConfig
import com.example.interrapidisimo.databinding.LoginActivityBinding
import com.example.interrapidisimo.ui.viewmodel.ControlVersionViewModel
import com.example.interrapidisimo.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private lateinit var binding: LoginActivityBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val checkAppVersion: ControlVersionViewModel by viewModels()
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userEditText.setText("cGFtLm1lcmVkeTIx\n")
        binding.passwordEditText.setText("SW50ZXIyMDIx\n")

        checkVersion()
        addObservers()
        addButtonsListeners()
    }

    private fun checkVersion() {
        val localVersion = BuildConfig.VERSION_NAME

        checkAppVersion.postControlVersion(
            localVersion = localVersion
        )
    }

    private fun addButtonsListeners() {
        binding.loginButton.setOnClickListener {
            val username = binding.userEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.validateLogIn(username, password)
        }

        binding.versionLayout.setOnClickListener {
            checkVersion()
        }

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.passwordEditText.background = ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_button_white)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.userEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    binding.userEditText.background = ContextCompat.getDrawable(this@LoginActivity, R.drawable.ic_button_white)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }

    private fun addObservers() {

        checkAppVersion.versionMessage.observe(this) { message ->
            showMessageVersionDialog(message)
        }
        checkAppVersion.showOrHideLoader.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        loginViewModel.showOrHideLoader.observe(this) { show ->
            showLoading(show)
        }
        loginViewModel.errorMessage.observe(this) { message ->
            message?.let {
                showErrorDialog(it)
            }
        }
        loginViewModel.successMessage.observe(this) { message ->
            message?.let {
                goToMainActivity()
            }
        }
        loginViewModel.isUserEmpty.observe(this) { data ->
            if (data) {
                binding.userEditText.error = getString(R.string.user_empty)
                binding.userEditText.background = ContextCompat.getDrawable(this, R.drawable.ic_button_red)
            }
        }

        loginViewModel.isPasswordEmpty.observe(this) { data ->
            if (data) {
                binding.passwordEditText.error = getString(R.string.password_empty)
                binding.passwordEditText.background = ContextCompat.getDrawable(this, R.drawable.ic_button_red)
            }
        }

    }

    private fun showLoading(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                loadingDialog = AlertDialog.Builder(this)
                    .setView(R.layout.dialog_loading)
                    .setCancelable(false)
                    .create()
                loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    private fun showErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)
        dialog.show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMessageVersionDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.version_info))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)
        dialog.show()
    }


}

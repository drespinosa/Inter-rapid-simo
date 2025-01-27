package com.example.interrapidisimo.ui.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.dto.request.RequestControlVDTO
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

        binding.userEditText.setText("cGFtLm1lcmVkeTIx\\n")
        binding.passwordEditText.setText("SW50ZXIyMDIx\\n")

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
            loginViewModel.validateAndLogIn(username, password)
        }

        binding.versionLayout.setOnClickListener {
            checkVersion()
        }
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

    }

    private fun showLoading(show: Boolean) {
        if (show) {
            if (loadingDialog == null) {
                loadingDialog = AlertDialog.Builder(this)
                    .setView(R.layout.dialog_loading)
                    .setCancelable(false)
                    .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMessageVersionDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.version_info))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .show()
    }


}

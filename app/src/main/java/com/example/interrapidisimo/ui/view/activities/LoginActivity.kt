package com.example.interrapidisimo.ui.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.BuildConfig
import com.example.interrapidisimo.data.utils.Constants.VERSION_EQUAL
import com.example.interrapidisimo.data.utils.Constants.VERSION_LOCAL_LARGER
import com.example.interrapidisimo.data.utils.Constants.VERSION_REMOTE_LARGER
import com.example.interrapidisimo.databinding.DialogVersionBinding
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
    private var versionRemote: Int? = null
    private var versionLocal: Int = 0
    private var isInitProcess: Boolean = true

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

    /**
     * Verifica la versión de la aplicación comparando la versión local con la remota.
     * Este método obtiene la versión local y solicita la versión remota al ViewModel.
     */
    private fun checkVersion() {
        versionLocal = BuildConfig.VERSION_NAME.toDouble().toInt()

        checkAppVersion.postControlVersion(
            localVersion = versionLocal
        )
    }

    private fun addButtonsListeners() {
        binding.loginButton.setOnClickListener {
            val username = binding.userEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.validateLogIn(username, password)
        }

        binding.versionLayout.setOnClickListener {
            isInitProcess = false
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
            if (isInitProcess) {
                setVersionImage(binding.warningVersionImageview, message)
            } else {
                showMessageVersionDialog(message)
            }
        }
        checkAppVersion.showOrHideLoader.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        checkAppVersion.versionRemote.observe(this) { version ->
            versionRemote = version
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
        val newMessage = getString(
            R.string.message_version,
            message,
            "\n \n- Versión Local: $versionLocal  \n- Versión Remota: $versionRemote"
        )
        val binding = DialogVersionBinding.inflate(LayoutInflater.from(this))
        binding.textDialogTextview.text = newMessage
        setVersionImage(binding.imageVersionDialogImageview, message)
        val dialog = AlertDialog.Builder(this).setView(binding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)
        dialog.show()
    }

    /**
     * Obtiene el recurso de imagen correspondiente al estado de la versión.
     *
     * @param state El estado de la versión.
     * @return El recurso de imagen correspondiente al estado.
     */
    private fun getVersionDrawable(state: String): Int {
        return when (state) {
            VERSION_REMOTE_LARGER -> R.drawable.ic_error
            VERSION_LOCAL_LARGER -> R.drawable.ic_change
            VERSION_EQUAL -> R.drawable.ic_check
            else -> R.drawable.ic_check
        }
    }

    /**
     * Establece la imagen correspondiente al estado de la versión en un ImageView.
     *
     * @param imageView El ImageView donde se establecerá la imagen.
     * @param state El estado de la versión.
     */
    private fun setVersionImage(imageView: ImageView, state: String) {
        imageView.setImageDrawable(ContextCompat.getDrawable(this, getVersionDrawable(state)))
        binding.versionNumberTextview.text = versionLocal.toString()
    }
}

package com.example.interrapidisimo.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.interrapidisimo.R
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.databinding.ActivityMainBinding
import com.example.interrapidisimo.ui.view.FragmentEventListener
import com.example.interrapidisimo.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentEventListener {

    private lateinit var binding: ActivityMainBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private var dataUser: UserVO? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setupHeaderButtons()
        loadFragment()

        loginViewModel.getUser()
        addObservers()
    }

    private fun setupHeaderButtons() {
        binding.buttonBack.setOnClickListener {
            goToHome()
        }

        binding.buttonLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    /**
     * Muestra un diálogo de confirmación para cerrar sesión.
     * Si el usuario confirma, se ejecuta la lógica de cierre de sesión.
     */
    private fun showLogoutConfirmationDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.logout)
            .setMessage(R.string.confirm_logout)
            .setPositiveButton(R.string.confirm) { dialog, _ ->
                loginViewModel.deleteUser()
                logout()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.canel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_background)
        dialog.show()
    }

    private fun goToHome() {
        navController.navigate(R.id.navigation_fragment_home)
    }

    /**
     * Cierra la sesión del usuario y redirige a la actividad de login.
     * Este método inicia la actividad de login y finaliza la actividad actual.
     */
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

    /**
     * Carga un fragmento en el contenedor de fragmentos.
     * Este método reemplaza el fragmento actual en el contenedor de fragmentos.
     *
     * @param fragment El fragmento que se va a cargar.
     */
    private fun loadFragment() {
        navController.navigate(R.id.navigation_fragment_home)
    }

    /**
     * Implementación de la interfaz [FragmentEventListener].
     * Este método habilita o deshabilita el botón de retroceso según el estado del fragmento.
     *
     * @param shouldDisable Indica si el botón de retroceso debe deshabilitarse (true) o habilitarse (false).
     */
    override fun onDisableButton(shouldDisable: Boolean) {
        if (shouldDisable) {
            binding.buttonBack.visibility = View.GONE
        } else {
            binding.buttonBack.visibility = View.VISIBLE
        }
    }
}

package com.example.interrapidisimo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.dto.request.RequestControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.domain.PostControlVUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ControlVersionViewModel @Inject constructor(
    private val controlVersionUseCase: PostControlVUseCase
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader
    private val _versionMessage = MutableLiveData<String>()
    val versionMessage: LiveData<String> get() = _versionMessage

    fun postControlVersion(request: RequestControlVDTO, localVersion: String) {
        Log.d("http ${this::class.java.simpleName}", "VM postControlVersion localVersion: $localVersion")

        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                controlVersionUseCase.invoke(
                    request,
                    object : ServiceUseCaseResponse<Response<ResponseDataControlVDTO>> {
                        override fun onSuccess(result: Response<ResponseDataControlVDTO>) {
                            Log.d("http ${this::class.java.simpleName}", "VM postControlVersion result: $result")

                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val remoteVersion  = result.body()?.version
                                    val comparison = compareVersions(localVersion, remoteVersion)

                                    when {
                                        comparison < 0 -> {
                                            _versionMessage.postValue("La versión de tu aplicación está desactualizada. Actualiza a la versión $remoteVersion.")
                                        }
                                        comparison > 0 -> {
                                            _versionMessage.postValue("Estás usando una versión más reciente que la oficial ($remoteVersion).")
                                        }
                                        else -> {
                                            _versionMessage.postValue("Tu aplicación está actualizada. Estás usando la última versión oficial ($localVersion).")
                                        }
                                    }

                                } else {
                                    Log.d("http ${this::class.java.simpleName}", "VM isError message: ${result.message()}")
                                    _versionMessage.postValue("Servicio para valdiar el control de version no disponible.")
                                }
                            } catch (e: Exception) {
                                Log.d("http ${this::class.java.simpleName}", "VM postControlVersion e: $e")
                                _versionMessage.postValue("Error al procesar la solicitud: ${e.message}")
                            } finally {
                                _showOrHideLoader.postValue(false)
                            }

                        }

                        override fun onError(apiError: ApiError?) {
                            Log.d("http ${this::class.java.simpleName}", "VM postControlVersion apiError: $apiError")

                            _showOrHideLoader.postValue(false)
                            _versionMessage.postValue("Servicio para valdiar el control de version no disponible.")
                        }
                    }
                )
            }
        }
    }

    fun compareVersions(localVersion: String, remoteVersion: String?): Int {
        Log.d("http ${this::class.java.simpleName}", "VM compareVersions localVersion: $localVersion")
        Log.d("http ${this::class.java.simpleName}", "VM compareVersions remoteVersion: $remoteVersion")

        if (remoteVersion == null) {
            return 0
        }
        val remoteParts = remoteVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val localParts = localVersion.split(".").map { it.toIntOrNull() ?: 0 }

        for (i in 0 until maxOf(localParts.size, remoteParts.size)) {
            val localPart = localParts.getOrElse(i) { 0 }
            val remotePart = remoteParts.getOrElse(i) { 0 }
            if (localPart != remotePart) {
                return localPart - remotePart
            }
        }
        return 0
    }

}
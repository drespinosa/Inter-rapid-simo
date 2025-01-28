package com.example.interrapidisimo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.utils.Constants.FAIL_RESPONSE_VERSION
import com.example.interrapidisimo.data.utils.Constants.VERSION_EQUAL
import com.example.interrapidisimo.data.utils.Constants.VERSION_LOCAL_LARGER
import com.example.interrapidisimo.data.utils.Constants.VERSION_REMOTE_LARGER
import com.example.interrapidisimo.domain.GetControlVersionUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ControlVersionViewModel @Inject constructor(
    private val controlVersionUseCase: GetControlVersionUseCase
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader

    private val _versionMessage = MutableLiveData<String>()
    val versionMessage: LiveData<String> get() = _versionMessage

    private val _versionRemote = MutableLiveData<String>()
    val versionRemote: LiveData<String> get() = _versionRemote

    private val _versionLocal = MutableLiveData<String>()
    val versionLocal: LiveData<String> get() = _versionLocal

    fun postControlVersion(localVersion: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                controlVersionUseCase.invoke(
                    null,
                    object : ServiceUseCaseResponse<Response<ResponseDataControlVDTO>> {
                        override fun onSuccess(result: Response<ResponseDataControlVDTO>) {
                            Log.d("http ${this::class.java.simpleName}", "VM postControlVersion result: $result")

                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val remoteVersion  = result.body()?.version ?: "0"
                                    val comparison = compareVersions(localVersion, remoteVersion)

                                    when {
                                        comparison < 0 -> {
                                            _versionMessage.postValue(VERSION_REMOTE_LARGER)
                                        }
                                        comparison > 0 -> {
                                            _versionMessage.postValue(VERSION_LOCAL_LARGER)
                                        }
                                        else -> {
                                            _versionMessage.postValue(VERSION_EQUAL)
                                        }
                                    }

                                } else {
                                    _versionMessage.postValue(FAIL_RESPONSE_VERSION)
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
                            _versionMessage.postValue(FAIL_RESPONSE_VERSION)
                        }
                    }
                )
            }
        }
    }

    fun compareVersions(localVersion: String, remoteVersion: String): Int {
        val remoteParts = remoteVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val localParts = localVersion.split(".").map { it.toIntOrNull() ?: 0 }

        _versionRemote.postValue(remoteVersion)
        _versionLocal.postValue(localVersion)

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
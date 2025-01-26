package com.example.interrapidisimo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.domain.GetLocalitiesUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LocalityViewModel @Inject constructor(
    private val localitiesUseCase: GetLocalitiesUseCase
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    fun getLocalities() {
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                localitiesUseCase.invoke(
                    null,
                    object : ServiceUseCaseResponse<Response<ResponseDataLocalityDTO>> {
                        override fun onSuccess(result: Response<ResponseDataLocalityDTO>) {
                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val localities = result.body()

                                    _successMessage.postValue("¡Inicio de sesión exitoso!")
                                } else {
                                    _errorMessage.postValue("Error al procesar la solicitud: ${result.message()}")
                                }
                            } catch (e: Exception) {
                                _errorMessage.postValue("Error al procesar la solicitud: ${e.message}")
                            } finally {
                                _showOrHideLoader.postValue(false)
                            }

                        }

                        override fun onError(apiError: ApiError?) {
                            _showOrHideLoader.postValue(false)

                            _errorMessage.postValue("Error: $apiError")
                        }
                    }
                )
            }
        }
    }

}
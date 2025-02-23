package com.example.interrapidisimo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.domain.GetLocalitiesUseCase
import com.example.interrapidisimo.domain.SaveLocalityUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LocalityViewModel @Inject constructor(
    private val localitiesUseCase: GetLocalitiesUseCase,
    private val saveLocalityUseCase: SaveLocalityUseCase,
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<Response<List<ResponseDataLocalityDTO>>>()
    val successMessage: LiveData<Response<List<ResponseDataLocalityDTO>>> get() = _successMessage

    fun getLocalities() {
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                localitiesUseCase.invoke(
                    null,
                    object : ServiceUseCaseResponse<Response<List<ResponseDataLocalityDTO>>> {
                        override fun onSuccess(result: Response<List<ResponseDataLocalityDTO>>) {
                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val tables = result.body()
                                    if (tables != null) {
                                        saveLocality(tables)
                                    }
                                    _successMessage.postValue(result)
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
                            if (apiError != null) {
                                _errorMessage.postValue("Error: ${apiError.getErrorMessage()}")
                            }
                        }
                    }
                )
            }
        }
    }

    fun saveLocality(tables: List<ResponseDataLocalityDTO>) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(viewModelScope.coroutineContext) {
                saveLocalityUseCase.insert(tables)
            }
        }
    }

}
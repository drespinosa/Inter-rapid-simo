package com.example.interrapidisimo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.domain.GetSchemaUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SchemaViewModel @Inject constructor(
    private val schemaUseCase: GetSchemaUseCase
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()
    private val _successMessage = MutableLiveData<Response<List<ResponseDataSchemeDTO>>>()

    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader
    val errorMessage: LiveData<String?> get() = _errorMessage
    val successMessage: LiveData<Response<List<ResponseDataSchemeDTO>>> get() = _successMessage

    fun getSchemas() {
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                schemaUseCase.invoke(
                    null,
                    object : ServiceUseCaseResponse<Response<List<ResponseDataSchemeDTO>>> {
                        override fun onSuccess(result: Response<List<ResponseDataSchemeDTO>>) {
                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val user = result.body()

                                    _successMessage.postValue(result)
                                } else {
                                    _errorMessage.postValue("Error: ${result.message()}")
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
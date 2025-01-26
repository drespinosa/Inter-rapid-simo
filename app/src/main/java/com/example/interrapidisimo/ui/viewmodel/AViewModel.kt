package com.example.interrapidisimo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.domain.GetRandomUseCae
import com.example.interrapidisimo.domain.GetUseCae
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AViewModel @Inject constructor(
    private val getUseCae: GetUseCae,
    private val getRandomUseCae: GetRandomUseCae
) : ViewModel() {

    private val _model = MutableLiveData<Model>()
    private val _showOrHideLoader = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String?>()

    val model: LiveData<Model> get() = _model
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun getQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                getUseCae.invoke(
                    null,
                    object : ServiceUseCaseResponse<Response<List<Model>>> {
                        override fun onSuccess(result: Response<List<Model>>) {
                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    if (!result.body().isNullOrEmpty()) {
                                        Log.d(
                                            "http ${this::class.java.simpleName}",
                                            "VM onCreate result: $result"
                                        )
                                        _model.postValue(result.body()?.random())
                                        _showOrHideLoader.postValue(false)
                                    } else {
                                        _errorMessage.postValue("Error: ${result.message()}")
                                    }
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

    fun randomModel() {
        _showOrHideLoader.postValue(true)
        val quoteRandom = getRandomUseCae()
        Log.d("http ${this::class.java.simpleName}", "VM randomModel quoteRandom: $quoteRandom")

        if (quoteRandom != null) {
            _model.postValue(quoteRandom!!)
        }
        _showOrHideLoader.postValue(false)
    }

}
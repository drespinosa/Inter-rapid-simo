package com.example.interrapidisimo.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.data.utils.Constants.FAIL_RESPONSE
import com.example.interrapidisimo.data.utils.Constants.RETRY_CREDENTIAL
import com.example.interrapidisimo.data.utils.Constants.SUCCESS_LOGIN
import com.example.interrapidisimo.domain.DeleteUserUseCase
import com.example.interrapidisimo.domain.GetUserByIdUseCase
import com.example.interrapidisimo.domain.PostLogInUseCase
import com.example.interrapidisimo.domain.SaveUserUseCase
import com.example.interrapidisimo.domain.ServiceUseCaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: PostLogInUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserByIdUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _showOrHideLoader = MutableLiveData<Boolean>()
    val showOrHideLoader: LiveData<Boolean> get() = _showOrHideLoader

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _dataUser = MutableLiveData<UserVO?>()
    val dataUser: LiveData<UserVO?> get() = _dataUser

    private val _isUserEmpty = MutableLiveData<Boolean>()
    val isUserEmpty: LiveData<Boolean> get() = _isUserEmpty

    private val _isPasswordEmpty = MutableLiveData<Boolean>()
    val isPasswordEmpty: LiveData<Boolean> get() = _isPasswordEmpty

    fun postLogIn(request: RequestUserDTO) {
        Log.d("http ${this::class.java.simpleName}", "VM postLogIn request: $request")
        viewModelScope.launch(Dispatchers.IO) {
            _showOrHideLoader.postValue(true)

            withContext(viewModelScope.coroutineContext) {
                logInUseCase.invoke(
                    request,
                    object : ServiceUseCaseResponse<Response<ResponseDataUserDTO>> {
                        override fun onSuccess(result: Response<ResponseDataUserDTO>) {
                            Log.d("http ${this::class.java.simpleName}", "VM postLogIn result: $result")
                            _showOrHideLoader.postValue(false)

                            try {
                                if (result.isSuccessful) {
                                    val user = result.body()
                                    if (user != null) {
                                        saveUser(user)
                                        _successMessage.postValue(SUCCESS_LOGIN)
                                    } else {
                                        _errorMessage.postValue(RETRY_CREDENTIAL)
                                    }
                                } else {
                                    Log.d("http ${this::class.java.simpleName}", "VM postLogIn isError message: ${result.message()}")
                                    _errorMessage.postValue(FAIL_RESPONSE + " ${result.message()}")
                                }
                            } catch (e: Exception) {
                                _errorMessage.postValue(FAIL_RESPONSE + " ${e.message}")
                            } finally {
                                _showOrHideLoader.postValue(false)
                            }
                        }

                        override fun onError(apiError: ApiError?) {
                            Log.d("http ${this::class.java.simpleName}", "VM postLogIn apiError: $apiError")
                            _showOrHideLoader.postValue(false)
                            if (apiError != null) {
                                _errorMessage.postValue(FAIL_RESPONSE + " ${apiError.getErrorMessage()}")
                            }
                        }
                    }
                )
            }
        }
    }

    fun validateLogIn(username: String, password: String) {
        val isUsernameValid = validateUsername(username)
        val isPasswordValid = validatePassword(password)

        if (isUsernameValid && isPasswordValid) {
            val request = createLoginRequest(username, password)
            postLogIn(request)
        }
    }

    private fun validateUsername(username: String): Boolean {
        return if (username.isBlank()) {
            _isUserEmpty.postValue(true)
            false
        } else {
            _isUserEmpty.postValue(false)
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isBlank()) {
            _isPasswordEmpty.postValue(true)
            false
        } else {
            _isPasswordEmpty.postValue(false)
            true
        }
    }

    private fun createLoginRequest(username: String, password: String): RequestUserDTO {
        return RequestUserDTO(
            mac = "",
            nameApp = "Controller APP",
            password = password,
            path = "",
            user = username
        )
    }

    fun saveUser(user: ResponseDataUserDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(viewModelScope.coroutineContext) {
                Log.d("http ${this::class.java.simpleName}", "VM saveUser user: $user")
                saveUserUseCase.insert(user)
            }
        }
    }

    fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(viewModelScope.coroutineContext) {
                deleteUserUseCase.delete()
            }
        }
    }

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(viewModelScope.coroutineContext) {
                _dataUser.postValue(getUserUseCase.get())
            }
        }
    }

}
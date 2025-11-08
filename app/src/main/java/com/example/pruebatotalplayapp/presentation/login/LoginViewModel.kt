package com.example.pruebatotalplayapp.presentation.login


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onUsernameChange(username: String) {
        _state.value = _state.value.copy(username = username, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, errorMessage = null)
    }

    fun login() {

            val currentState = _state.value
            if (currentState.username == "admin" && currentState.password == "admin") {
                _state.value = currentState.copy(
                    isSuccess = true,
                    errorMessage = null

                )
            } else {
                _state.value = currentState.copy(
                    errorMessage = "Usuario o contraseña incorrectos"
                )
            }

    }

    fun resetState() {
        _state.value = LoginState()
    }
}
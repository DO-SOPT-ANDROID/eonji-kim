package org.sopt.dosopttemplate.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(id: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authService.login(RequestLoginDto(id, password))
            }.onSuccess {
                if (it.body() != null) {
                    _loginState.value = LoginState.Success(it.body()!!)
                }
            }.onFailure {
                _loginState.value = LoginState.Error
            }
        }
    }
}
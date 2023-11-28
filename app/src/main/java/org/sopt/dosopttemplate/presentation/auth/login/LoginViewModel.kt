package org.sopt.dosopttemplate.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<ResponseLoginDto> = MutableLiveData()
    val loginResult: MutableLiveData<ResponseLoginDto> = _loginResult

    private val _loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = _loginSuccess

    fun login(id: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authService.login(RequestLoginDto(id, password))
            }.onSuccess {
                if (it.isSuccessful) {
                    loginResult.value = it.body()
                    loginSuccess.value = true
                } else {
                    loginSuccess.value = false
                }
            }.onFailure {
                // 에러 처리 로직
            }
        }
    }
}
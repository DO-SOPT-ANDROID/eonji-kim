package org.sopt.dosopttemplate.presentation.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto

class SignUpViewModel : ViewModel() {
    private val _signUpSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val signUpSuccess: MutableLiveData<Boolean> = _signUpSuccess

    fun signUp(id: String, name: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authService.signUp(RequestSignUpDto(id, name, password))
            }.onSuccess {
                signUpSuccess.value = it.isSuccessful
            }.onFailure {
                // 에러 처리 로직
            }
        }
    }
}
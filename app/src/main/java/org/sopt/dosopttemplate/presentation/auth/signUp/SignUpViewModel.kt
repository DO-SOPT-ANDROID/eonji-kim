package org.sopt.dosopttemplate.presentation.auth.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Loading)
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun signUp(id: String, name: String, password: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authService.signUp(RequestSignUpDto(id, name, password))
            }.onSuccess {
                _signUpState.value = SignUpState.Success(it.body()!!)
            }.onFailure {
                _signUpState.value = SignUpState.Error
            }
        }
    }
}
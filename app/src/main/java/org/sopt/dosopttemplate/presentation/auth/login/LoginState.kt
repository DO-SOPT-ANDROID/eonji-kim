package org.sopt.dosopttemplate.presentation.auth.login

import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val data: ResponseLoginDto) : LoginState()
    object Error : LoginState()
}

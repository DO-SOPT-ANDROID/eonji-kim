package org.sopt.dosopttemplate.presentation.auth.signUp

sealed class SignUpState {
    object Loading : SignUpState()
    data class Success(val data: Unit) : SignUpState()
    object Error : SignUpState()
}
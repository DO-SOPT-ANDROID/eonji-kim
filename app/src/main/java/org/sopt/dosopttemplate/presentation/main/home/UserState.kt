package org.sopt.dosopttemplate.presentation.main.home

import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDto

sealed class UserState {
    object Loading : UserState()
    data class Success(val data: ResponseUserListDto) : UserState()
    object Error : UserState()
}
package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.userService

class UserViewModel : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun getUserList(page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                userService.getUserList(page)
            }.onSuccess {
                if (it.body() != null) {
                    _userState.value = UserState.Success(it.body()!!)
                }
            }.onFailure {
                _userState.value = UserState.Error
            }
        }
    }
}
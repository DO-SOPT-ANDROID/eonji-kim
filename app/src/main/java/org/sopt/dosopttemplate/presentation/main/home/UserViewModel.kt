package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.userService
import org.sopt.dosopttemplate.server.auth.response.ResponseUserListDto

class UserViewModel : ViewModel() {
    private val _userResult: MutableLiveData<ResponseUserListDto> = MutableLiveData()
    val userResult: MutableLiveData<ResponseUserListDto> = _userResult

    private val _userSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val userSuccess: MutableLiveData<Boolean> = _userSuccess

    fun getUserList(page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                userService.getUserList(page)
            }.onSuccess {
                if (it.isSuccessful) {
                    userResult.value = it.body()
                    userSuccess.value = true
                } else {
                    userSuccess.value = false
                }
            }.onFailure {
                // 에러 처리 로직
            }
        }
    }
}
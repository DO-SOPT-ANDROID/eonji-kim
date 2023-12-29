package org.sopt.dosopttemplate.presentation.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto
import java.util.regex.Pattern

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

    fun checkId(input: String): Boolean {
        // 영문, 숫자 포함 6~10글자 이내
        val pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d).{6,10}$")
        return pattern.matcher(input).matches()
    }

    fun checkPw(input: String): Boolean {
        // 영문, 숫자, 특수문자 포함 6~12글자 이내
        val pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{6,12}$")
        return pattern.matcher(input).matches()
    }

    fun checkName(input: String): Boolean {
        // 문자 1글자 이상
        return input.trim().isNotEmpty()
    }

    fun checkMbti(input: String): Boolean {
        // 영문 포함 4글자
        val pattern = Pattern.compile(".*[a-zA-Z]{4}$")
        return pattern.matcher(input).matches()
    }
}
package org.sopt.dosopttemplate.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.auth.signUp.SignUpActivity
import org.sopt.dosopttemplate.presentation.main.HomeActivity
import org.sopt.dosopttemplate.util.makeToast

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInBtnClickListener()
        loginBtnClickListener()
        observeLoginResult()
    }

    private fun signInBtnClickListener() {
        binding.btnLoginSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun observeLoginResult() {
        loginViewModel.loginSuccess.observe(this) {
            if (it) {
                val userId = loginViewModel.loginResult.value?.id
                val userName = loginViewModel.loginResult.value?.username
                val userNickname = loginViewModel.loginResult.value?.nickname
                binding.root.makeToast("로그인 성공! userId: {$userId}")

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("UserId", userId)
                    putExtra("UserName", userName)
                    putExtra("UserNickname", userNickname)
                }
                startActivity(intent)
            } else {
                binding.root.makeToast("로그인 실패")
            }
        }
    }

    private fun loginBtnClickListener() {
        binding.btnLoginLogin.setOnClickListener {
            val id = binding.etLoginId.text.toString()
            val password = binding.etLoginPw.text.toString()

            loginViewModel.login(
                id = id,
                password = password,
            )
        }
    }
}
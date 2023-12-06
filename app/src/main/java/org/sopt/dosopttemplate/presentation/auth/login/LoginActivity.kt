package org.sopt.dosopttemplate.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
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
    }

    private fun signInBtnClickListener() {
        binding.btnLoginSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            loginViewModel.loginState.collect { loginState ->
                when (loginState) {
                    is LoginState.Success -> {
                        val userId = loginState.data.id
                        val userName = loginState.data.username
                        val userNickname = loginState.data.nickname
                        binding.root.makeToast("로그인 성공! userId: {$userId}")

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                            putExtra("UserId", userId)
                            putExtra("UserName", userName)
                            putExtra("UserNickname", userNickname)
                        }
                        startActivity(intent)
                    }

                    is LoginState.Error -> {
                        binding.root.makeToast("로그인 실패")
                    }

                    is LoginState.Loading -> {
                        binding.root.makeToast("로그인 중")
                    }
                }
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

            observeLoginResult()
        }
    }
}
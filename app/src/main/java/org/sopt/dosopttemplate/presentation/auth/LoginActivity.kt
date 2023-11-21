package org.sopt.dosopttemplate.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.HomeActivity
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto
import org.sopt.dosopttemplate.util.makeSnackbar
import org.sopt.dosopttemplate.util.makeToast
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var id: String? = null
    private var pw: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setResult()

        signInBtnClickListener()
        loginBtnClickListener()
    }

    private fun setResult() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // 서브 액티비티로부터 돌아올 때의 결과 값을 받아 올 수 있는 구문
            if (result.resultCode == RESULT_OK) {
                id = result.data?.getStringExtra("idValue") ?: ""
                pw = result.data?.getStringExtra("pwValue") ?: ""

                binding.root.makeSnackbar("회원가입에 성공하였습니다!")
            }
        }
    }

    private fun signInBtnClickListener() {
        binding.btnLoginSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun loginBtnClickListener() {
        binding.btnLoginLogin.setOnClickListener {
            val loginId = binding.etLoginId.text.toString()
            val loginPw = binding.etLoginPw.text.toString()

            authService.login(RequestLoginDto(loginId, loginPw))
                .enqueue(object : retrofit2.Callback<ResponseLoginDto> {
                    override fun onResponse(
                        call: Call<ResponseLoginDto>,
                        response: Response<ResponseLoginDto>
                    ) {
                        if (response.isSuccessful) {
                            val data: ResponseLoginDto? = response.body()
                            val userId = data?.id
                            val userName = data?.username
                            val userNickname = data?.nickname

                            binding.root.makeToast("로그인을 성공하였고 유저의 ID는 $userId 입니다")

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("idValue", userId)
                            intent.putExtra("nameValue", userName)
                            intent.putExtra("nickNameValue", userNickname)

                            startActivity(intent)
                        } else {
                            binding.root.makeToast("로그인에 실패했습니다.")
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                        binding.root.makeToast("서버 에러 발생")
                    }
                })
        }
    }
}
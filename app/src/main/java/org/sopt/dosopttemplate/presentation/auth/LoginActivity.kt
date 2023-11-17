package org.sopt.dosopttemplate.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.databinding.ActivityLoginBinding
import org.sopt.dosopttemplate.presentation.main.HomeActivity
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestLoginDto
import org.sopt.dosopttemplate.server.auth.response.ResponseLoginDto
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var id: String? = null
    private var pw: String? = null
    /*
    private var name: String? = null
    private var mbti: String? = null
    private var birth: String? = null
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)    // equal findViewById()

        setResult()

        signBtnClickListener()
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
                // name = result.data?.getStringExtra("nameValue") ?: ""
                // mbti = result.data?.getStringExtra("mbtiValue")?.uppercase(Locale.ROOT) ?: ""
                // birth = result.data?.getStringExtra("birthValue") ?: ""
                makeSnackbar("회원가입에 성공하였습니다!")
            }
        }
    }

    fun signBtnClickListener() {
        binding.btnLoginSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // startActivity(intent)
            resultLauncher.launch(intent)
        }
    }

    fun loginBtnClickListener() {
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
                            val data: ResponseLoginDto = response.body()!!
                            val userId = data.id
                            val userName = data.username
                            val userNickname = data.nickname

                            makeToast("로그인을 성공하였고 유저의 ID는 $userId 입니다")

                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("idValue", userId)
                            intent.putExtra("nameValue", userName)
                            intent.putExtra("nickNameValue", userNickname)

                            startActivity(intent)
                        }
                        else {
                            makeToast("로그인에 실패했습니다.")
                        }
                    }

                    override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                        makeToast("서버 에러 발생")
                    }
                })

            /*
            if (binding.etLoginId.text.toString() == id && binding.etLoginPw.text.toString() == pw
            ) {
                makeToast("로그인에 성공했습니다!")
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("idValue", id)
                intent.putExtra("pwValue", pw)
                intent.putExtra("nameValue", name)
                intent.putExtra("mbtiValue", mbti)
                intent.putExtra("birthValue", birth)
                startActivity(intent)
            } else {
                makeToast("로그인에 실패했습니다.")
            }
             */
        }
    }

    fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
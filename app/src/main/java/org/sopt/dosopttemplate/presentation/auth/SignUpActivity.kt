package org.sopt.dosopttemplate.presentation.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.server.ServicePool.authService
import org.sopt.dosopttemplate.server.auth.request.RequestSignUpDto
import org.sopt.dosopttemplate.util.makeSnackbar
import org.sopt.dosopttemplate.util.makeToast
import retrofit2.Call
import retrofit2.Response
import java.util.Calendar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateBtnClickListener()
        signUpBtnClickListener()
    }

    @SuppressLint("SetTextI18n")
    private fun dateBtnClickListener() {
        binding.ivSignUpDateBtn.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.tvSignUpDate.text =
                    year.toString() + "/" + (month + 1).toString() + "/" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun signUpBtnClickListener() {
        binding.btnSignUpSignUp.setOnClickListener {
            val signUpName = binding.etSignUpId.text.toString()
            val signUpNickname = binding.etSignUpName.text.toString()
            val signUpPw = binding.etSignUpPw.text.toString()
            val signUpMbti = binding.etSignUpMbti.text.toString()
            val signUpBirth = binding.tvSignUpDate.text.toString()

            if (signUpName.isEmpty() || signUpPw.isEmpty() || signUpNickname.isEmpty() || signUpMbti.isEmpty() || signUpBirth.isEmpty()) {
                binding.root.makeSnackbar("모든 정보를 입력해 주세요.")
            } else if (signUpName.length in 6..10 && signUpPw.length in 8..12 && signUpNickname.trim()
                    .isNotEmpty() && signUpMbti.length == 4
            ) {
                authService.signUp(
                    RequestSignUpDto(
                        signUpName,
                        signUpNickname,
                        signUpPw
                    )
                )
                    .enqueue(object : retrofit2.Callback<Unit> {
                        override fun onResponse(
                            call: Call<Unit>,
                            response: Response<Unit>
                        ) {
                            if (response.isSuccessful) {
                                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                intent.putExtra("idValue", signUpName)
                                intent.putExtra("pwValue", signUpPw)
                                setResult(RESULT_OK, intent)
                                finish()
                            } else {
                                binding.root.makeToast(
                                    "회원가입에 실패했습니다. ${
                                        response.errorBody()?.string()
                                    }"
                                )
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            binding.root.makeToast("서버 에러 발생")
                        }
                    })
            } else {
                binding.root.makeSnackbar("입력 정보를 다시 확인해 주세요.")
            }
        }
    }
}
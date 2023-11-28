package org.sopt.dosopttemplate.presentation.auth.signUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.auth.login.LoginActivity
import org.sopt.dosopttemplate.util.makeSnackbar
import org.sopt.dosopttemplate.util.makeToast
import java.util.Calendar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

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
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.tvSignUpDate.text =
                    year.toString() + "/" + (month + 1).toString() + "/" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun observeSignUpResult() {
        signUpViewModel.signUpSuccess.observe(this) {
            if (it) {
                binding.root.makeToast("회원가입 성공!")
                startActivity(
                    Intent(
                        this@SignUpActivity,
                        LoginActivity::class.java
                    )
                )
            } else {
                binding.root.makeToast("회원가입 실패")
            }
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
                signUpViewModel.signUp(
                    id = signUpName,
                    name = signUpNickname,
                    password = signUpPw,
                )

                observeSignUpResult()

            } else {
                binding.root.makeSnackbar("입력 정보를 다시 확인해 주세요.")
            }
        }
    }
}
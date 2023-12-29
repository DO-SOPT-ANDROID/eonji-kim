package org.sopt.dosopttemplate.presentation.auth.signUp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import org.sopt.dosopttemplate.presentation.auth.login.LoginActivity
import org.sopt.dosopttemplate.util.makeToast
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()

    private var idFlag = false
    private var pwFlag = false
    private var nameFlag = false
    private var mbtiFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutSignUpId.editText?.addTextChangedListener(idListener)
        binding.layoutSignUpPw.editText?.addTextChangedListener(pwListener)
        binding.layoutSignUpName.editText?.addTextChangedListener(nameListener)
        binding.layoutSignUpMbti.editText?.addTextChangedListener(mbtiListener)

        signUpBtnClickListener()
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

            signUpViewModel.signUp(
                id = signUpName,
                name = signUpNickname,
                password = signUpPw,
            )

            observeSignUpResult()
        }
    }

    fun flagCheck() {
        binding.btnSignUpSignUp.isEnabled = idFlag && pwFlag && nameFlag && mbtiFlag
    }

    private val idListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.layoutSignUpId.error = "아이디를 입력해주세요."
                        idFlag = false
                    }

                    !signUpViewModel.checkId(s.toString()) -> {
                        binding.layoutSignUpId.error = "숫자와 영문자 조합으로 6~10자를 사용해주세요."
                        idFlag = false
                    }

                    else -> {
                        binding.layoutSignUpId.error = null
                        idFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val pwListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.layoutSignUpPw.error = "비밀번호를 입력해주세요."
                        pwFlag = false
                    }

                    !signUpViewModel.checkPw(s.toString()) -> {
                        binding.layoutSignUpPw.error = "숫자, 영문자, 특수문자 조합으로 6~10자를 사용해주세요."
                        pwFlag = false
                    }

                    else -> {
                        binding.layoutSignUpPw.error = null
                        pwFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val nameListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.layoutSignUpName.error = "닉네임을 입력해주세요."
                        nameFlag = false
                    }

                    !signUpViewModel.checkName(s.toString()) -> {
                        binding.layoutSignUpName.error = "한 글자 이상 사용해주세요."
                        nameFlag = false
                    }

                    else -> {
                        binding.layoutSignUpName.error = null
                        nameFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val mbtiListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.layoutSignUpMbti.error = "mbti를 입력해주세요."
                        mbtiFlag = false
                    }

                    !signUpViewModel.checkMbti(s.toString()) -> {
                        binding.layoutSignUpMbti.error = "영문자 조합으로 네 글자 사용해주세요."
                        mbtiFlag = false
                    }

                    else -> {
                        binding.layoutSignUpMbti.error = null
                        mbtiFlag = true
                    }
                }
                flagCheck()
            }
        }
    }
}
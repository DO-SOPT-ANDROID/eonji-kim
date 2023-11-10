package org.sopt.dosopttemplate

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.sopt.dosopttemplate.databinding.ActivitySignUpBinding
import java.util.Calendar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    // calendar
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dateBtnClickListener()
        initSignBtnClickListener()
    }

    @SuppressLint("SetTextI18n")
    fun dateBtnClickListener() {
        binding.ivSignUpDateBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                binding.tvSignUpDate.text =
                    year.toString() + "/" + (month + 1).toString() + "/" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    fun initSignBtnClickListener() {
        binding.btnSignUpSignUp.setOnClickListener {
            if (binding.etSignUpId.text.isNullOrBlank() || binding.etSignUpPw.text.isNullOrBlank() || binding.etSignUpName.text.isNullOrBlank() || binding.etSignUpMbti.text.isNullOrBlank() || binding.tvSignUpDate.text.isNullOrBlank()) {
                makeSnackbar("모든 정보를 입력해 주세요.")
            } else if (binding.etSignUpId.text.toString().length in 6..10 && binding.etSignUpPw.text.toString().length >= 8 && binding.etSignUpPw.text.toString().length <= 12 && binding.etSignUpName.text.toString()
                    .trim().isNotEmpty() && binding.etSignUpMbti.text.toString().length == 4
            ) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("idValue", binding.etSignUpId.text.toString())
                intent.putExtra("pwValue", binding.etSignUpPw.text.toString())
                intent.putExtra("nameValue", binding.etSignUpName.text.toString())
                intent.putExtra("mbtiValue", binding.etSignUpMbti.text.toString())
                intent.putExtra("birthValue", binding.tvSignUpDate.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            } else {
                makeSnackbar("입력 정보를 다시 확인해주세요.")
            }
        }
    }

    fun makeSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
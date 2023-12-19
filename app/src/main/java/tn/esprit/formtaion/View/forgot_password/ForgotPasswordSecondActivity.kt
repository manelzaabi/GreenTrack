package tn.esprit.formtaion.View.forgot_password

import ForgotPasswordViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Utils.ForgotPasswordService

class ForgotPasswordSecondActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_second)

        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        viewModel.context = this

        val otpDigit1: EditText = findViewById(R.id.otpDigit1)
        val otpDigit2: EditText = findViewById(R.id.otpDigit2)
        val otpDigit3: EditText = findViewById(R.id.otpDigit3)
        val otpDigit4: EditText = findViewById(R.id.otpDigit4)
        val verifyOtpButton: Button = findViewById(R.id.verifyOtpButton)

        var token = ""

        verifyOtpButton.setOnClickListener {
            val otpCode = "${otpDigit1.text}${otpDigit2.text}${otpDigit3.text}${otpDigit4.text}"
            token = intent.getStringExtra("token") ?: ""

            if (otpCode.length == 4) {
                val body = ForgotPasswordService.VerifyResetCodeBody(otpCode, token)
                viewModel.verifyResetCode(body)
            } else {
                Toast.makeText(this, "Code invalid", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.result.observe(this) {
            val intent = Intent(this, ForgotPasswordThirdActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }
}

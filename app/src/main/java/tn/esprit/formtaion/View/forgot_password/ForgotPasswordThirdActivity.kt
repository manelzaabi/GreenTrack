package tn.esprit.formtaion.View.forgot_password

import ForgotPasswordViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Utils.ForgotPasswordService
import tn.esprit.formtaion.View.LoginActivity

class ForgotPasswordThirdActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_third)

        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        viewModel.context = this

        val newPasswordEditText: EditText = findViewById(R.id.editText2)
        val verifyPasswordEditText: EditText = findViewById(R.id.editText3)
        val submitButton: Button = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString().trim()
            val verifyPassword = verifyPasswordEditText.text.toString().trim()

            if (newPassword.isNotEmpty() && newPassword == verifyPassword) {
                val token = intent.getStringExtra("token")

                if (token != null) {
                    val body = ForgotPasswordService.ResetPasswordBody(token, newPassword)
                    viewModel.resetPassword(body)
                }
            }
        }

        viewModel.result.observe(this) {
            navigateToNextActivity(LoginActivity::class.java)
        }
    }

    private fun navigateToNextActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}

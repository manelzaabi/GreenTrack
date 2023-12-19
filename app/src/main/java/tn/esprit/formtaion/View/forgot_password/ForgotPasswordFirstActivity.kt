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

class ForgotPasswordFirstActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_first)

        viewModel = ViewModelProvider(this)[ForgotPasswordViewModel::class.java]
        viewModel.context = this

        val emailEditText: EditText = findViewById(R.id.editText)
        val sendButton: Button = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                val body = ForgotPasswordService.ForgotPasswordBody(email)
                viewModel.forgotPassword(body)
            }
        }

        viewModel.result.observe(this) { response ->
            val intent = Intent(this, ForgotPasswordSecondActivity::class.java)
            intent.putExtra("token", response.token)
            startActivity(intent)
        }
    }

}

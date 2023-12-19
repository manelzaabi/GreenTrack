package tn.esprit.formtaion.View

import ForgotPasswordViewModel
import UserViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Utils.AuthToken
import tn.esprit.formtaion.Utils.ForgotPasswordService
import tn.esprit.formtaion.Utils.UserService

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.context = this

        val editTextCurrentPassword: EditText = findViewById(R.id.editTextCurrentPassword)
        val editTextNewPassword: EditText = findViewById(R.id.editTextNewPassword)
        val editTextNewPasswordConfirmation: EditText =
            findViewById(R.id.editTextNewPasswordConfirmation)
        val saveButton: Button = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val currentPassword = editTextCurrentPassword.text.toString().trim()
            val newPassword = editTextNewPassword.text.toString().trim()
            val newPasswordConfirmation = editTextNewPasswordConfirmation.text.toString().trim()

            if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirmation.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (newPassword != newPasswordConfirmation) {
                Toast.makeText(this, "Password should match confirmation", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Password should at least 6 character", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            viewModel.changePassword(
                UserService.ChangePasswordBody(
                    AuthToken.getInstance(this).userId!!,
                    currentPassword,
                    newPassword
                )
            )
        }

        viewModel.result.observe(this) {
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
    }
}

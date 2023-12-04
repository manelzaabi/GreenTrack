package tn.esprit.formtaion.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Repository.AuthRepository
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.Utils.VibrateView
import tn.esprit.formtaion.ViewModel.LoginActivityViewModel
import tn.esprit.formtaion.ViewModel.LoginActivityViewModelFactory
import tn.esprit.formtaion.data.LoginBody
import tn.esprit.formtaion.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener , View.OnFocusChangeListener , View.OnKeyListener {

    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var  mViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.loginWithGooge.setOnClickListener(this)
        mBinding.loginBtn.setOnClickListener(this)
        mBinding.registerBtn.setOnClickListener(this)
        mBinding.privacypolicy.setOnClickListener(this)
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.passwordEt.setOnKeyListener(this)
        mViewModel = ViewModelProvider(this, LoginActivityViewModelFactory(AuthRepository(APIService.getService()), application)).get(LoginActivityViewModel::class.java)

        setupObservers()

    }

    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
            mBinding.progressBar.isVisible = it
        }
        mViewModel.getErrorMessage().observe(this) {
            //fullname , emaim , passwd
            val formErrorKeys = arrayOf("fullname", "email", "phone", "password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains((entry.key))) {
                    when (entry.key) {


                        "email" -> {
                            mBinding.emailTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }



                        "password" -> {
                            mBinding.passwordTil.apply {
                                isErrorEnabled = true
                                error = entry.value
                            }
                        }

                    }

                } else {
                    message.append(entry.value).append("\n")
                }
                if (message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("Information")
                        .setMessage(message)
                        .setPositiveButton("OK") { dialog, _ -> dialog!!.dismiss() }
                        .show()
                }
            }
        }
        mViewModel.getUser().observe(this) {
            Log.d("RegisterActivity", "User observed: $it")
            if (it != null) {
                //  startActivity(Intent(this , LoginActivity::class.java))
                intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                startActivity(intent)

            }
        }
    }
    private fun validationEmail(shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.emailEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email address is invalid"
        }
        if (errorMessage != null) {
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)

            }
        }

        return errorMessage == null
    }


    private fun validatePassword(
        shouldUpdateView: Boolean = true,
        shouldVibrateView: Boolean = true
    ): Boolean {
        var errorMessage: String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@LoginActivity, this)

            }
        }
        return errorMessage == null
    }
    private fun validate(): Boolean {
        var isValid = true

        if (!validationEmail(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false
        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }

    /*override fun onClick(view: View?) {
        if (view != null){
            when(view.id){
                R.id.loginBtn -> {
                    submitForm()
                }
                R.id.registreBtn -> {
                   // startActivity(Intent(this,RegisterActivity::class.java))
                    val registerButton: Button = findViewById(R.id.registreBtn)
                    registerButton.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

                }
            }
        }
    }*/
    override fun onClick(view: View?) {
        if (view != null){
            when(view.id){
                R.id.loginBtn -> {
                    submitForm()
                }
                R.id.registerBtn -> {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }
                R.id.privacypolicy -> {
                    startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                }
            }
        }
    }


    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {


                R.id.emailEt -> {
                    if (hasFocus) {
                        if (mBinding.emailTil.isErrorEnabled) {
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    } else {
                        validationEmail()
                    }

                }



                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }

                    } else {
                         validatePassword()
                    }

                }



            }
        }
    }
    private fun submitForm(){
        if (validate()){
            //verify user credentials
            mViewModel.loginUser(LoginBody(mBinding.emailEt.text!!.toString() , mBinding.passwordEt.text!!.toString()))
        }
    }
    override fun onKey(view: View?, KeyCode: Int, keyEvent: KeyEvent?): Boolean {
    if(KeyCode == KeyEvent.KEYCODE_ENTER && keyEvent!!.keyCode ==KeyEvent.ACTION_UP){
        submitForm()
    }

        return false
    }
}
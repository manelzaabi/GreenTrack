package tn.esprit.formtaion.View

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Repository.AuthRepository
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.Utils.ApiConsummer
import tn.esprit.formtaion.Utils.VibrateView
import tn.esprit.formtaion.ViewModel.RegisterActivityViewModel
import tn.esprit.formtaion.ViewModel.RegisterActivityViewModelFactory
import tn.esprit.formtaion.data.RegisterBody
import tn.esprit.formtaion.data.ValidateEmailBody
import tn.esprit.formtaion.databinding.ActivityRegisterBinding
import java.lang.StringBuilder

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener, TextWatcher {

    private lateinit var mBinding: ActivityRegisterBinding
    private  lateinit var mViewModel: RegisterActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.fullNameEt.onFocusChangeListener = this
        mBinding.emailEt.onFocusChangeListener = this
        mBinding.phoneEt.onFocusChangeListener = this
        mBinding.passwordEt.onFocusChangeListener = this
        mBinding.verifypasswordEt.onFocusChangeListener = this
        mBinding.verifypasswordEt.setOnKeyListener(this)
        mBinding.verifypasswordEt.addTextChangedListener(this)
        mBinding.registreBtn.setOnClickListener(this)
        mViewModel = ViewModelProvider(this, RegisterActivityViewModelFactory(AuthRepository(APIService.getService()), application)).get(RegisterActivityViewModel::class.java)
        setupObservers()
    }
    private fun setupObservers() {
        mViewModel.getIsLoading().observe(this) {
                mBinding.progressBar.isVisible = it
        }
        mViewModel.getErrorMessage().observe(this){
        //fullname , emaim , passwd
            val formErrorKeys = arrayOf("fullname","email","phone","password")
            val message = StringBuilder()
            it.map { entry ->
                if (formErrorKeys.contains((entry.key))) {
                when (entry.key){
                    "fullname" -> {
                    mBinding.fullNameTil.apply {
                        isErrorEnabled = true
                        error = entry.value
                    }
                    }
                    "email" -> {
                        mBinding.emailTil.apply {
                            isErrorEnabled = true
                            error = entry.value
                        }
                    }
                    "phone" -> {
                        mBinding.phoneTil.apply {
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

                }else{
                    message.append(entry.value).append("\n")
                }
                if(message.isNotEmpty()) {
                    AlertDialog.Builder(this)
                        .setIcon(R.drawable.info_24)
                        .setTitle("Information")
                        .setMessage(message)
                        .setPositiveButton("OK"){dialog, _ -> dialog!!.dismiss()}
                        .show()
                }
            }
        }
        mViewModel.getUser().observe(this) {
            Log.d("RegisterActivity", "User observed: $it")
            if(it != null){
              //  startActivity(Intent(this , LoginActivity::class.java))
                intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)

            }
        }
    }
    private fun validateFullname( shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value: String = mBinding.fullNameEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Fullname is required"
        }
        if (errorMessage != null) {
            mBinding.fullNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)
            }
        }

        return errorMessage == null
    }

    private fun validationEmail( shouldVibrateView: Boolean = true): Boolean {
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
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)

            }
        }

        return errorMessage == null
    }

    /*private fun validationEmail(): Boolean {
        var errorMessage: String? = null
        val value = mBinding.emailEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email adresse are invalid"
        }
        if (errorMessage != null) {
            mBinding.emailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }


        return errorMessage == null
    }*/


    private fun validationPhone( shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.phoneEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Phone Number is required"
        }
        if (errorMessage != null) {
            mBinding.phoneTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)

            }
        }
        return errorMessage == null
    }

    private fun validatePassword(shouldUpdateView: Boolean = true , shouldVibrateView: Boolean = true ): Boolean {
        var errorMessage: String? = null
        val value = mBinding.passwordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be 6 characters long"
        }
        if (errorMessage != null && shouldUpdateView ) {
            mBinding.passwordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)

            }
        }
        return errorMessage == null
    }


    private fun validateConformPassword(shouldUpdateView: Boolean = true , shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val value = mBinding.verifypasswordEt.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm password is required"
        } else if (value.length < 6) {
            errorMessage = "Conform password must be 6 characters long"
        }
        if (errorMessage != null && shouldUpdateView ) {
            mBinding.virifypasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)

            }
        }
        return errorMessage == null
    }

    private fun validatePasswordAndVerifyPassword(shouldUpdateView: Boolean = true , shouldVibrateView: Boolean = true): Boolean {
        var errorMessage: String? = null
        val password = mBinding.passwordEt.text.toString()
        val verifypassword = mBinding.verifypasswordEt.text.toString()

        if (password != verifypassword) {
            errorMessage = "Verify password doesn't match with password"
        }
        if (errorMessage != null && shouldUpdateView) {
            mBinding.virifypasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
                if (shouldVibrateView) VibrateView.vibrate(this@RegisterActivity , this)

            }
        }

        return errorMessage == null

    }


    override fun onClick(view: View?) {
        if (view != null && view.id == R.id.registreBtn)
            onSubmit()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.fullNameEt -> {
                    if (hasFocus) {
                        if (mBinding.fullNameTil.isErrorEnabled) {
                            mBinding.fullNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateFullname()
                    }
                }

                R.id.emailEt -> {
                    if (hasFocus) {
                        if (mBinding.emailTil.isErrorEnabled) {
                            mBinding.emailTil.isErrorEnabled = false
                        }
                    } else {
                        if (validationEmail()) {
                            mViewModel.validateEmailAdress(ValidateEmailBody(mBinding.emailEt.text!!.toString()))                        }
                    }

                }

                R.id.phoneEt -> {
                    if (hasFocus) {
                        if (mBinding.phoneTil.isErrorEnabled) {
                            mBinding.phoneTil.isErrorEnabled = false
                        }
                    } else {
                        validationPhone()
                    }

                }

                R.id.passwordEt -> {
                    if (hasFocus) {
                        if (mBinding.passwordTil.isErrorEnabled) {
                            mBinding.passwordTil.isErrorEnabled = false
                        }

                    } else {
                        if (validatePassword() && mBinding.verifypasswordEt.text!!.isNotEmpty() && validateConformPassword() && validatePasswordAndVerifyPassword()) {
                            if (mBinding.virifypasswordTil.isErrorEnabled) {
                                mBinding.virifypasswordTil.isErrorEnabled = false
                            }
                            mBinding.virifypasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }

                }


                R.id.verifypasswordEt -> {
                    if (hasFocus) {
                        if (mBinding.virifypasswordTil.isErrorEnabled) {
                            mBinding.virifypasswordTil.isErrorEnabled = false
                        }

                    } else {
                        if (validateConformPassword() && validatePassword() && validatePasswordAndVerifyPassword()) {
                            if (mBinding.passwordTil.isErrorEnabled) {
                                mBinding.passwordTil.isErrorEnabled = false
                            }
                            mBinding.virifypasswordTil.apply {
                                setStartIconDrawable(R.drawable.check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
    if (KeyEvent.KEYCODE_ENTER == keyCode && keyEvent!!.action == KeyEvent.ACTION_UP){
            onSubmit()
    }
        return false
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (validatePassword(shouldUpdateView = false) && validateConformPassword(shouldUpdateView = false) && validatePasswordAndVerifyPassword(shouldUpdateView = false)){
            mBinding.virifypasswordTil.apply {
                if(isErrorEnabled) isErrorEnabled = false
                setStartIconDrawable(R.drawable.check_circle_24)
                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            }
        }else {
            if (mBinding.virifypasswordTil.startIconDrawable != null)
                mBinding.virifypasswordTil.startIconDrawable = null
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    private fun onSubmit(){
    if (validate()) {
        mViewModel.registerUser(RegisterBody(mBinding.fullNameEt.text!!.toString() , mBinding.emailEt.text!!.toString(),mBinding.phoneEt.text!!.toString(),mBinding.passwordEt.text!!.toString()))
            }
    }

    private fun validate() : Boolean {
        var isValid = true

        if (!validateFullname(shouldVibrateView = false)) isValid = false
        if (!validationEmail(shouldVibrateView = false)) isValid = false
        if (!validationPhone(shouldVibrateView = false)) isValid = false
        if (!validatePassword(shouldVibrateView = false)) isValid = false
        if (!validateConformPassword(shouldVibrateView = false)) isValid = false
        if (isValid && !validatePasswordAndVerifyPassword(shouldVibrateView = false)) isValid = false
        if (!isValid) VibrateView.vibrate(this, mBinding.cardView)

        return isValid
    }
}
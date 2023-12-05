package tn.esprit.formtaion.View

import ProfileViewModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.Utils.AuthToken
import tn.esprit.formtaion.data.AuthResponse
import tn.esprit.formtaion.databinding.ProfileActivityBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authTokenInstance = AuthToken.getInstance(applicationContext)
        val authToken = authTokenInstance.token

        // Observer for authResponseLiveData
        profileViewModel.authResponseLiveData.observe(this, Observer { authResponse ->
            authResponse?.let {
                bindDataToViews(it)
                println("Auth Response Observed: $authResponse")
            }
        })

        profileViewModel.errorLiveData.observe(this, Observer { errorMessage ->
            // Handle errors here
            errorMessage?.let {
                println("Error: $it")
            }
        })

        profileViewModel.authenticateProfile(authToken.toString())

        binding.editBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.passwordBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindDataToViews(authResponse: AuthResponse) {
        println("bindDataToViews Called")

        if (authResponse.user.fullname != null) {
            binding.fullname.text = authResponse.user.fullname
        }
        if (authResponse.user.email != null) {
            binding.emailAff.text = authResponse.user.email
        }

        if (authResponse.user.fullname != null) {
            binding.textView3.text = authResponse.user.fullname
        }
        if (authResponse.user.email != null) {
            binding.textView4.text = authResponse.user.email
        }
    }
}

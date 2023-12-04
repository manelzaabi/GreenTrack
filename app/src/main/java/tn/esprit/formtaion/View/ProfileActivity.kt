package tn.esprit.formtaion.View

import ProfileViewModel
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
                // Add a log to check if this observer is called
                println("Auth Response Observed: $authResponse")
            }
        })

        // Observer for errorLiveData
        profileViewModel.errorLiveData.observe(this, Observer { errorMessage ->
            // Handle errors here
            errorMessage?.let {
                println("Error: $it")
                // You might want to display an error message to the user
            }
        })

        profileViewModel.authenticateProfile(authToken.toString())
    }


    private fun bindDataToViews(authResponse: AuthResponse) {
        // Add logs to check if this function is called
        println("bindDataToViews Called")

        if (authResponse.user.fullname != null) {
            binding.fullname.text = authResponse.user.fullname
        }
        if (authResponse.user.email != null) {
            binding.emailAff.text = authResponse.user.email
        }

    }
}

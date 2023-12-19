package tn.esprit.formtaion.View

import ProfileViewModel
import UserViewModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import tn.esprit.formtaion.CalculActivity
import tn.esprit.formtaion.EventActivity
import tn.esprit.formtaion.SecondActivity
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.Utils.AuthToken
import tn.esprit.formtaion.activities.NewMain
import tn.esprit.formtaion.data.AuthResponse
import tn.esprit.formtaion.data.User
import tn.esprit.formtaion.databinding.ProfileActivityBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editBtn.isEnabled = false
        var user: User? = null

        // Observer for authResponseLiveData
        userViewModel.userLiveData.observe(this) {
            bindDataToViews(it)
            user = it
            binding.editBtn.isEnabled = true
            println("Auth Response Observed: ${it.email}")
        }

        userViewModel.userLiveData.observe(this, Observer { errorMessage ->
            // Handle errors here
            errorMessage?.let {
                println("Error: $it")
            }
        })


        binding.editBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            intent.putExtra("user", user!!)
            startActivity(intent)
        }

        binding.passwordBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.eventbtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EventActivity::class.java)
            startActivity(intent)
        }
        binding.vidbtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, NewMain::class.java)
            startActivity(intent)
        }

        binding.mapbtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, SecondActivity::class.java)
            startActivity(intent)
        }
        binding.calvulBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, CalculActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val authTokenInstance = AuthToken.getInstance(applicationContext)
        val userId = authTokenInstance.userId

        userViewModel.getById(userId!!)
    }

    private fun bindDataToViews(user: User) {
        println("bindDataToViews Called")

        binding.fullname.text = user.fullname
        binding.emailAff.text = user.email
        binding.textView3.text = user.fullname
        binding.textView4.text = user.email

        Glide.with(this).load(
            APIService.BASE_URL + "/public/" + user.image
        ).into(binding.imageView)
    }
}

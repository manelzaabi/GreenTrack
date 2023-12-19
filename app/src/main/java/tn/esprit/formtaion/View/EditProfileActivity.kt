package tn.esprit.formtaion.View

import UserViewModel
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import tn.esprit.formtaion.R
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.data.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextFullName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var imageView: ImageView
    private lateinit var addImageButton: Button
    private lateinit var saveButton: Button

    private lateinit var userViewModel: UserViewModel
    private lateinit var user: User

    private var imageIsChanged: Boolean = true;

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, you can proceed with image-related operations
            } else {
                // Permission denied, handle accordingly
                // You might want to show a message to the user or disable certain features
            }
        }

    private val imageSelector =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the selected image
                val selectedImageUri = result.data?.data
                // Update your UI or upload the image
                // For simplicity, let's set the image URI to the ImageView
                imageView.setImageURI(selectedImageUri)

                Glide.with(this).load(selectedImageUri).into(imageView)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        // Initialize ViewModel
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.context = this

        // Check if the permission is granted
        if (isReadStoragePermissionGranted()) {
            // Permission is already granted, you can proceed with image-related operations
        } else {
            // Permission is not granted, request it
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        initializeViews()

        saveButton.setOnClickListener {
            val fullname: String = editTextFullName.text.toString()
            val email: String = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()

            var imageFile: File? = null

            if (imageIsChanged) {
                imageFile = getFileFromBitmap(imageView)

                if (imageFile == null) {
                    showToast("Please choose an image.")
                    return@setOnClickListener
                }
            }

            if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showToast("Please fill in all fields.")
                return@setOnClickListener
            }

            if (phone.length != 8) {
                showToast("Phone invalid.")
                return@setOnClickListener
            }

            user.fullname = fullname
            user.email = email
            user.phone = phone

            userViewModel.update(user, imageFile!!)
            userViewModel.userLiveData.observe(this) {
                finish()
            }
        }

        addImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imageSelector.launch(intent)
        }
    }

    private fun getFileFromBitmap(imageView: ImageView): File? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap

            // Get the context wrapper
            val wrapper = ContextWrapper(applicationContext)

            // Initialize a new file instance to save bitmap object
            var file: File = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file, "${UUID.randomUUID()}.jpg")

            try {
                // Compress the bitmap and save in jpg format
                val stream: OutputStream = FileOutputStream(file)
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return file
        }

        return null
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initializeViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextPhone = findViewById(R.id.editTextPhone)
        imageView = findViewById(R.id.imageIV)
        addImageButton = findViewById(R.id.addImageButton)
        saveButton = findViewById(R.id.saveButton)

        user = (intent?.getSerializableExtra("user") as? User)!!

        editTextEmail.setText(user.email)
        editTextFullName.setText(user.fullname)
        editTextPhone.setText(user.phone)

        Glide.with(this).load(APIService.BASE_URL + "/public/" + user.image).into(imageView)
    }

    private fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Permissions are granted at installation time on devices running versions below M
            true
        }
    }
}

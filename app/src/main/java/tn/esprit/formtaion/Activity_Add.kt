package tn.esprit.formtaion

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.DatePicker
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formation.models.EventItem
import tn.esprit.formation.repositories.EventRepository
import tn.esprit.formation.service.RetrofitClient
import java.util.UUID

class Activity_Add : AppCompatActivity() {


    private var collector = ""

    private lateinit var titletxt: EditText
    private lateinit var desctxt: EditText
    private lateinit var loctxt: EditText
    private lateinit var detailsEvent: EditText
    private lateinit var SubmitSave: Button
    private lateinit var Freebtn: RadioButton
    private lateinit var Paidbtn: RadioButton
    private lateinit var datePicker: DatePicker
    private lateinit var imagePicker : Button
    private lateinit var imageView: ImageView

    private var selectedImageUri : Uri? = null

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            imageView.setImageURI(uri)

        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        val eventService = RetrofitClient.eventService
        val repository = EventRepository(eventService)




        titletxt = findViewById(R.id.titletxt)
        desctxt = findViewById(R.id.desctxt)
        loctxt = findViewById(R.id.loctxt)
        detailsEvent = findViewById(R.id.detailsEvent)
        Freebtn = findViewById(R.id.free)
        Paidbtn = findViewById(R.id.paid)
        datePicker = findViewById(R.id.datePicker)
        SubmitSave = findViewById(R.id.btnSubmit)
        imageView = findViewById(R.id.imageView)

        imagePicker = findViewById(R.id.imagePicker)

        imagePicker.setOnClickListener {
            // Registers a photo picker activity launcher in single-select mode.

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }

        SubmitSave.setOnClickListener {
            val title = titletxt.text.toString()
            val description = desctxt.text.toString()
            val location = loctxt.text.toString()
            val details = detailsEvent.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year

            val selectedDate = "$day/$month/$year"

            if (title.isEmpty() || selectedImageUri == null || description.isEmpty() || location.isEmpty() || details.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val eventItem = EventItem(
                    date = selectedDate,
                    description = description,
                    details = details,
                    id = UUID.randomUUID().toString(),
                    image = "image-1700598973149-918941309.png",
                    isFree = true,
                    location = location,
                    price = "23",
                    title = title
                )

                repository.addEvent(eventItem,selectedImageUri!!,this).observe(this) { it ->

                    if (it) {
                        Toast.makeText(
                            this@Activity_Add,
                            "Event added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@Activity_Add,
                            "Failed to add event",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        } else {

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    companion object {
        const val REQUEST_PERMISSION = 23
    }

}



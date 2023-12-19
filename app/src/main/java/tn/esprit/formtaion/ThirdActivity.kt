package tn.esprit.formtaion


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.material.button.MaterialButton
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage



class ThirdActivity : AppCompatActivity() {

    private lateinit var cameraBtn: MaterialButton
    private lateinit var galleryBtn: MaterialButton
    private lateinit var imageIv: ImageView
    private lateinit var scanBtn: MaterialButton
    private lateinit var resultTv: TextView

    companion object{
        private const val CAMERA_REQUEST_CODE = 100
        private const val STORAGE_REQUEST_CODE = 101

        private const val TAG = "MAIN_TAG"

    }

    private  lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    private var imageUri:Uri? = null

    private  var barcodeScannerOptions: BarcodeScannerOptions? = null
    private var barcodeScanner: BarcodeScanner? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        cameraBtn = findViewById(R.id.cameraBtn)
        galleryBtn = findViewById(R.id.galleryBtn)
        imageIv = findViewById(R.id.imageIv)
        scanBtn = findViewById(R.id.scanBtn)
        resultTv = findViewById(R.id.resultTv)

        cameraPermission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)


        barcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions!!)





        cameraBtn.setOnClickListener {
            if (checkCameraPermissions()){
                pickImageCamera()
            }
            else {
                requestCameraPermission()

            }


        }
        galleryBtn.setOnClickListener {
            if (checkStoragePermission()) {
                pickImageGallery()
            }else {
                requestStoragePermission()
            }

        }
        scanBtn.setOnClickListener {
            if ( imageUri == null) {
                showToast("Pick image first")


            }else {
                detectResultFromImage()
            }

        }
    }
    private fun detectResultFromImage() {
        Log.d(TAG, "detectResultFromImage: ")
        try {
            val inputImage = InputImage.fromFilePath(this, imageUri!!)
            val barcodeResult = barcodeScanner!!.process(inputImage)
                .addOnSuccessListener { barcodes ->

                    extractBarcodeQrCodeInfo(barcodes)


                }
                .addOnFailureListener {e ->
                    Log.e(TAG, "detectResultFrom Image:", e)
                    showToast("Failed scanning due to ${e.message}")

                }

        }
        catch (e: Exception) {
            Log.e(TAG, "detectResultFrom Image:", e)
            showToast("Failed due to ${e.message}")
        }
    }

    private fun extractBarcodeQrCodeInfo(barcodes: MutableList<com.google.mlkit.vision.barcode.common.Barcode>) {

        for (barcode in barcodes) {
            val bound = barcode.boundingBox
            val corners = barcode.cornerPoints
            val rawValue = barcode.rawValue
            Log.d(TAG, "extractBarcodeQrCodeInfo: rawValues: $rawValue")

            val valueType = barcode.format
            when (valueType) {
                Barcode.WIFI -> {
                    val typeWifi = barcode.wifi

                    val ssid = "${typeWifi?.ssid}"
                    var password = "${typeWifi?.password}"
                    var encryptionType = "${typeWifi?.encryptionType}"

                    if (encryptionType == "1") {
                        encryptionType = "OPEN"
                    } else if (encryptionType == "2") {
                        encryptionType = "WPA"
                    } else if (encryptionType == "3") {
                        encryptionType = "WEP"
                    }
                    Log.d(TAG, "extractBarcodeQrCodeInfo: TYPE_WIFI")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: ssid: $ssid")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: password: $password")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: encryptionType: $encryptionType")

                    resultTv.text = "TYPE_WIFI \nssid: $ssid \npassword: $password \nencryptionType:$encryptionType \n\nrawValue: $rawValue"

                }

                Barcode.URL -> {
                    val typeUrl = barcode.url

                    val title = "${typeUrl?.title}"
                    val url = "${typeUrl?.url}"

                    Log.d(TAG, "extractBarcodeQrCodeInfo: TYPE_URL")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: title: $title")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: url: $url")

                    resultTv.text = "TYPE_URL \ntitle: $title \nurl: $url \n\nrawValue: $rawValue"

                }
                Barcode.EMAIL -> {
                    val typeEmail = barcode.email
                    val address= "${typeEmail?.address}"
                    val body= "${typeEmail?.body}"
                    val subject= "${typeEmail?.subject}"

                    Log.d(TAG, "extractBarcodeQrCodeInfo: TYPE_EMAIL")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: address: $address")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: body: $body")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: subject: $subject")

                    resultTv.text = "TYPE_EMAIL \nEmail: $address \nbody: $body \nsubject: $subject \n\nrawValue: $rawValue"
                }
                Barcode.CONTACT_INFO -> {
                    val typeContract = barcode.contactInfo

                    val title = "${typeContract?.title}"
                    val organization = "${typeContract?.organization}"
                    val name = "${typeContract?.name?.first} ${typeContract?.name?.last}"
                    val phone = "${typeContract?.name?.first} ${typeContract?.phones?.get(0)?.number }"

                    Log.d(TAG, "extractBarcodeQrCodeInfo: TYPE_CONTACT_INFO")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: title: $title")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: organization: $organization")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: name: $name")
                    Log.d(TAG, "extractBarcodeQrCodeInfo: phone: $phone")

                    resultTv.text = "TYPE_CONTACT_INFO \ntitle: $title \norganization : $organization \nname: $name \nphone:$phone \n\nrawValue: $rawValue"

                }
                else -> {
                    resultTv.text = "rawValue: $rawValue"
                }
            }
        }

    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            imageUri = data?.data
            Log.d(TAG, "cameraActivityResultLauncher:imageUri: $imageUri ")

            imageIv.setImageURI(imageUri)
        }
        else {

            showToast("Cancelled....!")

        }
    }


    private fun pickImageCamera() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Sample Image")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Sample Image Description")

        imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()

    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            val data = result.data
            Log.d(TAG, "cameraActivityResultLauncher: imageUri:$imageUri ")

            imageIv.setImageURI(imageUri)
        }
    }

    private fun checkStoragePermission(): Boolean {



        val result =  (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED )
        return result;
    }


    private  fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)

    }

    private fun checkCameraPermissions(): Boolean {
        val resultCamera = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        val  resultStorage = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

        return resultCamera && resultStorage

    }


    private  fun requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted) {
                        pickImageCamera()
                    } else {
                        showToast("Camera & storage permissions are required ")
                    }
                }
            }

            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        pickImageGallery()
                    }
                    else {
                        showToast("Storage permission is required ...")
                    }

                }
            }


        }
    }

    private  fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


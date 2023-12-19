package tn.esprit.formtaion.View


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.formtaion.R
import tn.esprit.formtaion.api.ApiResponse
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formtaion.RetrofitClient
import java.io.FileNotFoundException
import java.io.InputStream

class SingleVideoPickActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private var selectedVideoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_video_pick)

        videoView = findViewById(R.id.videoView)
        val singleVideoPickBtn = findViewById<FloatingActionButton>(R.id.singleVideoPickBtn)
        val uploadButton = findViewById<FloatingActionButton>(R.id.uploadButton)
        val mediaController = MediaController(this)

        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

        val singleVideoLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                try {
                    videoView.setVideoURI(uri)
                    videoView.start()
                    selectedVideoUri = uri
                } catch (e: Exception) {
                    Log.e("SingleVideoPickActivity", "Error setting video URI: ${e.message}", e)
                    Toast.makeText(this@SingleVideoPickActivity, "Error setting video", Toast.LENGTH_SHORT).show()
                }
            }

        singleVideoPickBtn.setOnClickListener {
            singleVideoLauncher.launch("video/*")
        }

        uploadButton.setOnClickListener {
            uploadVideo()
        }
    }

    private fun uploadVideo() {
        // Generate a unique title by appending a timestamp
        val title = "VideoTitle_${System.currentTimeMillis()}"
        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())

        // Generate a unique filename by appending a timestamp
        val filename = "video_${System.currentTimeMillis()}.mp4"

        val videoInputStream: InputStream? = selectedVideoUri?.let {
            try {
                contentResolver.openInputStream(it)
            } catch (e: FileNotFoundException) {
                Log.e("SingleVideoPickActivity", "File not found: ${e.message}", e)
                null
            }
        }

        if (videoInputStream != null) {
            Log.d("SingleVideoPickActivity", "Starting video upload...")

            val videoFile = MultipartBody.Part.createFormData(
                "videoFile",
                filename,
                videoInputStream.readBytes().toRequestBody("video/*".toMediaTypeOrNull())
            )

            RetrofitClient.apiService.addVideoWithPath(titleRequestBody, videoFile)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            // Handle successful upload
                            Log.d("SingleVideoPickActivity", "Video uploaded successfully")
                            Toast.makeText(
                                this@SingleVideoPickActivity,
                                "Video uploaded successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish() // Finish the activity or perform any other necessary action
                        } else {
                            Log.e("SingleVideoPickActivity", "Error: ${response.code()}")
                            Toast.makeText(
                                this@SingleVideoPickActivity,
                                "Error: ${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("SingleVideoPickActivity", "Upload failure: ${t.message}", t)
                        Toast.makeText(
                            this@SingleVideoPickActivity,
                            "Error uploading video: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        } else {
            Log.e("SingleVideoPickActivity", "Error reading video file")
            Toast.makeText(this@SingleVideoPickActivity, "Error reading video file", Toast.LENGTH_SHORT).show()
        }
    }
}

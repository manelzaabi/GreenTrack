package  tn.esprit.formtaion.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tn.esprit.formtaion.R
import tn.esprit.formtaion.RetrofitClient
import tn.esprit.formtaion.api.ApiResponse
import tn.esprit.formtaion.View.SingleVideoPickActivity
import tn.esprit.formtaion.ViewModel.VideoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewMain : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var singleVideoPickBtn: FloatingActionButton
    private lateinit var refreshButton: FloatingActionButton
    private lateinit var likeButton: ImageButton

    private val apiService = RetrofitClient.apiService
    private var videoUrls: MutableList<String> = mutableListOf()
    private var currentVideoIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
        refreshButton = findViewById(R.id.refreshButton)

        // Initialize the like button
        likeButton = findViewById(R.id.likeButton)

        // Set a click listener for the like button
        likeButton.setOnClickListener {
            addLikeToVideo()
        }


        val goToSecondActivityButton: ImageButton = findViewById(R.id.goToSecondActivityButton)
        goToSecondActivityButton.setOnClickListener {
            // Create an Intent to start the second activity
            val intent = Intent(this, MainActivity2::class.java)

            // Start the second activity
            startActivity(intent)
        }
        videoView = findViewById(R.id.videoView)
        singleVideoPickBtn = findViewById(R.id.singleVideoPickBtn)
        refreshButton.setOnClickListener {
            videoViewModel.refreshVideos { success ->
                if (success) {
                    playNextVideo()
                    Toast.makeText(this@NewMain, "Video refreshed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@NewMain, "Error refreshing videos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        setupButtons()

        // Make API call to get videos
        apiService.getVideoNames().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        val videoNames = apiResponse.videoFiles
                        if (videoNames.isNotEmpty()) {
                            videoUrls.addAll(videoNames.map { name ->
                                "http://172.16.6.147:3000/video/videos/names/${Uri.encode(name)}"
                            })

                            playVideo(videoUrls.first())
                        } else {
                            Toast.makeText(this@NewMain, "No video names available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@NewMain, "Invalid response format", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@NewMain, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@NewMain, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupButtons() {
        singleVideoPickBtn.setOnClickListener {
            startActivity(Intent(this, SingleVideoPickActivity::class.java))
        }

        refreshButton.setOnClickListener {
            refreshVideos()
        }

        // Add a listener to the Next button
        val nextButton = findViewById<FloatingActionButton>(R.id.nextButton)
        nextButton.setOnClickListener {
            playNextVideo()

        }
    }

    private fun playVideo(videoUrl: String) {
        videoView.setVideoURI(Uri.parse(videoUrl))
        videoView.setOnPreparedListener { mp ->
            mp.start()
        }
        videoView.setOnCompletionListener { mp ->
            Toast.makeText(this@NewMain, "Video playback completed", Toast.LENGTH_SHORT).show()
            // Optionally play the next video when the current one completes
            playNextVideo()
        }
        videoView.requestFocus()
    }

    private fun playNextVideo() {
        if (videoUrls.isNotEmpty()) {
            currentVideoIndex = (currentVideoIndex + 1) % videoUrls.size
            val nextVideoPath = videoUrls[currentVideoIndex]
            Log.d("VideoNextPath", "Next video path: $nextVideoPath")
            playVideo(nextVideoPath)
        }
    }


    private fun refreshVideos() {
        // Implement video refreshing logic if needed
        // For simplicity, this example directly plays the first video from the response
        apiService.getVideoNames().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        val videoNames = apiResponse.videoFiles
                        if (videoNames.isNotEmpty()) {
                            videoUrls.clear()
                            videoUrls.addAll(videoNames.map { name ->
                                "http://172.16.6.147:3000/video/videos/names/${Uri.encode(name)}"
                            })

                            currentVideoIndex = 0
                            playVideo(videoUrls.first())
                            Toast.makeText(this@NewMain, "Video refreshed successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@NewMain, "No video names available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@NewMain, "Invalid response format", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@NewMain, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@NewMain, "Error refreshing videos: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun addLikeToVideo() {
        val videoId = "655114e424d2b6567f64e6ad" // Replace with the actual videoId

        // Assuming you have a function in your ApiService to add a like
        apiService.addLike(videoId).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // Handle success
                    Log.d("ApiRequest", "Add like request successful. Path: ${call.request().url}")
                    Toast.makeText(this@NewMain, "Like added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle failure
                    Log.e("ApiRequest", "Failed to add like. Path: ${call.request().url}")
                    Toast.makeText(this@NewMain, "Failed to add like", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Handle failure
                Log.e("ApiRequest", "Error: ${t.message}. Path: ${call.request().url}")
                Toast.makeText(this@NewMain, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



}

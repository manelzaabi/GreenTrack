package  tn.esprit.formtaion.ViewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import tn.esprit.formtaion.api.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formtaion.RetrofitClient


class VideoViewModel : ViewModel() {
    private val apiService = RetrofitClient.apiService
    private var videoUrls: MutableList<String> = mutableListOf()
    private var currentVideoIndex = 0
    fun refreshVideos(callback: (Boolean) -> Unit) {
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
                            callback.invoke(true)
                        } else {
                            callback.invoke(false)
                        }
                    } else {
                        callback.invoke(false)
                    }
                } else {
                    callback.invoke(false)
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                callback.invoke(false)
            }
        })
    }

    fun playNextVideo(callback: (String?) -> Unit) {
        if (videoUrls.isNotEmpty()) {
            currentVideoIndex = (currentVideoIndex + 1) % videoUrls.size
            val nextVideoPath = videoUrls[currentVideoIndex]
            callback.invoke(nextVideoPath)
        } else {
            callback.invoke(null)
        }
    }
}

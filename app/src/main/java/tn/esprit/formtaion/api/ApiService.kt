package  tn.esprit.formtaion.api


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("video/videos/name")
    fun getVideoNames(): Call<ApiResponse>

    @Multipart
    @POST("video/add")
    fun addVideoWithPath(
        @Part("title") title: RequestBody,
        @Part videoFile: MultipartBody.Part
    ): Call<ApiResponse>

    @POST("video/{videoId}/like")
    fun addLike(@Path("videoId") videoId: String): Call<ApiResponse>



}

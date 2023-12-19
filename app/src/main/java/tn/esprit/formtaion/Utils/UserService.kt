package tn.esprit.formtaion.Utils

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import tn.esprit.formtaion.data.User

interface UserService {

    @GET("/users/get-by-id/{id}")
    fun getById(@Path("id") id: String): Call<User>

    @Multipart
    @PUT("/users/{id}")
    fun update(
        @Path("id") id: String,
        @Part("fullname") fullName: String,
        @Part("email") email: String,
        @Part("num_tel") phone: String,
        @Part image: MultipartBody.Part?
    ): Call<User>

    data class ChangePasswordBody(
        val userId: String,
        val currentPassword: String,
        val newPassword: String
    )

    @POST("/users/change-password")
    fun changePassword(@Body body: ChangePasswordBody): Call<String>

}
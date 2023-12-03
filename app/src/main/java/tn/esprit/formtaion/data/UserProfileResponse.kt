package tn.esprit.formtaion.data

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("fullname") val fullname: String?,
    @SerializedName("profilepicture") val profilePicture: String?,
    @SerializedName("phonenumber") val phoneNumber: String?,
)

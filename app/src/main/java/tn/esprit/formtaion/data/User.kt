package tn.esprit.formtaion.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val _id: String,
    val fullname: String,
    val email: String,
    val phone: String
)

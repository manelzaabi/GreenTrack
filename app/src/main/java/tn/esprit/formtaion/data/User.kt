package tn.esprit.formtaion.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val id: String,
    var fullname: String,
    var email: String,
    @SerializedName("num_tel") var phone: String,
    @SerializedName("img") val image: String
) : Serializable

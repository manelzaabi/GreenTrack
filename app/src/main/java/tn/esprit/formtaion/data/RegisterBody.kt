package tn.esprit.formtaion.data

data class RegisterBody(
    val fullname: String,
    val email: String,
    val phone: String,
    val password: String
)

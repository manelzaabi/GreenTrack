package tn.esprit.formtaion.Utils

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import tn.esprit.formtaion.data.RegisterBody
import tn.esprit.formtaion.data.AuthResponse
import tn.esprit.formtaion.data.LoginBody
import tn.esprit.formtaion.data.UniqueEmailValidationResponse
import tn.esprit.formtaion.data.ValidateEmailBody

interface ApiConsummer {
    @POST("/users/validate-unique-email")
    suspend fun validateEmailAdress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @PUT("/auth/signup")
    suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>


    @POST("/auth/signin")
    suspend fun loginUser(@Body body: LoginBody): Response<AuthResponse>


}
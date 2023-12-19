package tn.esprit.formtaion.Utils

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordService {
    data class ForgotPasswordBody(
        val email: String,
    )

    data class VerifyResetCodeBody(
        val resetCode: String,
        val token: String
    )

    data class ResetPasswordBody(
        val token: String,
        val plainPassword: String
    )

    data class ResetPasswordResponse(
        val error: String,
        val token: String,
    )

    @POST("/forgot-password/send-email")
    fun forgotPassword(@Body body: ForgotPasswordBody): Call<ResetPasswordResponse>

    @POST("/forgot-password/verify-reset-code")
    fun verifyResetCode(@Body body: VerifyResetCodeBody): Call<ResetPasswordResponse>

    @POST("/forgot-password/reset-password")
    fun resetPassword(@Body body: ResetPasswordBody): Call<ResetPasswordResponse>
}
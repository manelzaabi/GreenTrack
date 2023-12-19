import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.Utils.ForgotPasswordService

class ForgotPasswordViewModel : ViewModel() {

    val result = MutableLiveData<ForgotPasswordService.ResetPasswordResponse>()

    private val forgotPasswordService = APIService.forgotPasswordService

    lateinit var context: Context

    fun forgotPassword(body: ForgotPasswordService.ForgotPasswordBody) {
        Toast.makeText(context, "Loading ..", Toast.LENGTH_SHORT).show()

        val call = forgotPasswordService.forgotPassword(body)

        call.enqueue(object : Callback<ForgotPasswordService.ResetPasswordResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                response: Response<ForgotPasswordService.ResetPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                t: Throwable
            ) {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                Log.d("MyApp", "Subject Update: " + t.message.toString())
            }
        })
    }

    fun verifyResetCode(body: ForgotPasswordService.VerifyResetCodeBody) {
        Toast.makeText(context, "Loading ..", Toast.LENGTH_SHORT).show()

        val call = forgotPasswordService.verifyResetCode(body)

        call.enqueue(object : Callback<ForgotPasswordService.ResetPasswordResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                response: Response<ForgotPasswordService.ResetPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                t: Throwable
            ) {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                Log.d("MyApp", "Subject Update: " + t.message.toString())
            }
        })
    }

    fun resetPassword(body: ForgotPasswordService.ResetPasswordBody) {
        Toast.makeText(context, "Loading ..", Toast.LENGTH_SHORT).show()

        val call = forgotPasswordService.resetPassword(body)

        call.enqueue(object : Callback<ForgotPasswordService.ResetPasswordResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                response: Response<ForgotPasswordService.ResetPasswordResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ForgotPasswordService.ResetPasswordResponse>,
                t: Throwable
            ) {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                Log.d("MyApp", "Subject Update: " + t.message.toString())
            }
        })
    }
}

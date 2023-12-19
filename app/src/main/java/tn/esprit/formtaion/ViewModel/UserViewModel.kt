import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.Utils.ForgotPasswordService
import tn.esprit.formtaion.Utils.UserService
import tn.esprit.formtaion.data.User

class UserViewModel : ViewModel() {

    val userLiveData = MutableLiveData<User>()
    val result = MutableLiveData<String>()

    private val userService = APIService.userService

    lateinit var context: Context

    fun getById(id: String) {
        val call = userService.getById(id)
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Log.w("response, ", response.toString())
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body())
                    Log.d("user", userLiveData.toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("MyApp", "Product list: " + t.message.toString())
            }
        })
    }

    fun update(user: User, imageFile: java.io.File?) {
        val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())

        var imagePart: MultipartBody.Part? = null

        if (requestFile != null) {
            imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        }

        Toast.makeText(context, "Loading ..", Toast.LENGTH_SHORT).show()

        val call = userService.update(
            user.id,
            user.fullname,
            user.email,
            user.phone,
            imagePart
        )

        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    userLiveData.value = response.body()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<User>,
                t: Throwable
            ) {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                Log.d("MyApp", "Subject Update: " + t.message.toString())
            }
        })
    }

    fun changePassword(body: UserService.ChangePasswordBody) {
        Toast.makeText(context, "Loading ..", Toast.LENGTH_SHORT).show()

        val call = userService.changePassword(body)

        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {
                    result.value = response.body()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<String>,
                t: Throwable
            ) {
                result.value = "Finish"
                Log.d("MyApp", "Subject Update: " + t.message.toString())
            }
        })
    }
}

// ProfileViewModel.kt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import tn.esprit.formtaion.Utils.APIService
import tn.esprit.formtaion.data.AuthResponse
import tn.esprit.formtaion.Utils.AuthToken
import tn.esprit.formtaion.data.User

class ProfileViewModel : ViewModel() {

    val authResponseLiveData: MutableLiveData<AuthResponse?> = MutableLiveData()
    val errorLiveData: MutableLiveData<String?> = MutableLiveData()
    val userLiveData: MutableLiveData<User?> = MutableLiveData()

    private val apiService = APIService.getService()

    fun authenticateProfile(authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Ajoutez un journal pour vérifier l'appel de la fonction
                println("authenticateProfile() called")

                println("Auth Token: $authToken")

                val response: Response<AuthResponse> = apiService.authenticateProfile("Bearer $authToken")

                // Ajoutez un journal pour vérifier la réponse de l'appel réseau
                println("API Call Response: $response")

                if (response.isSuccessful) {
                    // Handle the successful response
                    val authResponse = response.body()
                    val user = authResponse?.user

                    authResponseLiveData.postValue(authResponse)
                    userLiveData.postValue(user)

                    // Ajoutez des journaux pour vérifier les données
                    println("Auth Response: $authResponse")
                    println("User: $user")
                } else {
                    // Handle unsuccessful response (e.g., display an error message)
                    val errorMessage = "Erreur lors de l'authentification du profil : ${response.message()}"
                    errorLiveData.postValue(errorMessage)
                    println(errorMessage)
                }
            } catch (e: Exception) {
                // Handle network or other exceptions
                val errorMessage = "Erreur lors de l'authentification du profil : ${e.message}"
                errorLiveData.postValue(errorMessage)
                println(errorMessage)
                e.printStackTrace() // Imprimer la trace de la pile pour un débogage supplémentaire
            }
        }
    }



}

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.esprit.formtaion.Repository.AuthRepository
import tn.esprit.formtaion.Utils.RequestStatus
import tn.esprit.formtaion.data.EditBody

class EditProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // LiveData pour suivre l'état de chargement
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // LiveData pour afficher les messages liés à la mise à jour du profil
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    // Fonction pour effectuer la mise à jour du profil
    fun editProfile(userId: String, body: EditBody) {
        viewModelScope.launch {
            try {
                authRepository.editProfile(userId, body).collect { requestStatus ->
                    when (requestStatus) {
                        is RequestStatus.Waiting -> {
                            // _loading.value = true (Pas nécessaire ici, car déjà fait dans la LiveData)
                        }
                        is RequestStatus.Success -> {
                            _message.value = "Profile updated successfully"
                        }
                        is RequestStatus.Error -> {
                            _message.value = "Failed to update profile: ${requestStatus.message}"
                        }

                        else -> {}
                    }
                }
            } catch (e: Exception) {
                _message.value = "Error updating profile: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}

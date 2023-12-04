package tn.esprit.formtaion.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.esprit.formtaion.Repository.AuthRepository
import tn.esprit.formtaion.Utils.AuthToken
import tn.esprit.formtaion.Utils.RequestStatus
import tn.esprit.formtaion.data.RegisterBody
import tn.esprit.formtaion.data.User
import tn.esprit.formtaion.data.ValidateEmailBody

class RegisterActivityViewModel(val authRepository: AuthRepository, val application: Application) :
    ViewModel() {
    private var isLoading: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var isUniqueEmail: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().apply { value = false }
    private var user: MutableLiveData<User> = MutableLiveData()

    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getIsUniqueEmail(): LiveData<Boolean> = isUniqueEmail
    fun getUser(): LiveData<User> = user

    fun validateEmailAdress(body: ValidateEmailBody) {
        viewModelScope.launch {
            authRepository.validateEmailAdress(body).collect() {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        isUniqueEmail.value = it.data.isUnique

                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message

                    }
                }
            }
        }
    }

    fun registerUser(body: RegisterBody) {
        viewModelScope.launch {
            authRepository.registerUser(body).collect {
                when (it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }

                    is RequestStatus.Success -> {
                        isLoading.value = false
                        user.value = it.data.user
                        // save token using shared preferences
                        AuthToken.getInstance(application.baseContext).token = it.data.token

                    }

                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message

                    }
                }
            }
        }
    }
}


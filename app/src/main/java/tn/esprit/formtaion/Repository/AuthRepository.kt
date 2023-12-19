package tn.esprit.formtaion.Repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import tn.esprit.formtaion.Utils.ApiConsummer
import tn.esprit.formtaion.Utils.RequestStatus
import tn.esprit.formtaion.Utils.SimplifiedMessage
import tn.esprit.formtaion.data.AuthResponse
import tn.esprit.formtaion.data.EditBody
import tn.esprit.formtaion.data.LoginBody
import tn.esprit.formtaion.data.RegisterBody
import tn.esprit.formtaion.data.ValidateEmailBody

class AuthRepository(private val consummer: ApiConsummer) {
    fun validateEmailAdress(body: ValidateEmailBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consummer.validateEmailAdress(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(
                RequestStatus.Error(
                    SimplifiedMessage.get(
                        "{'message':'Email already in use'}"
                    )
                )
            )
        }

    }

    fun registerUser(body: RegisterBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consummer.registerUser(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(
                RequestStatus.Error(
                    SimplifiedMessage.get(
                        response.errorBody()!!.byteStream().reader().readText()
                    )
                )
            )
        }
    }


    fun loginUser(body: LoginBody) = flow {
        emit(RequestStatus.Waiting)
        val response = consummer.loginUser(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(
                RequestStatus.Error(
                    SimplifiedMessage.get(
                        response.errorBody()!!.byteStream().reader().readText()
                    )
                )
            )
        }
    }


    suspend fun editProfile(userId: String, body: EditBody): Flow<RequestStatus<AuthResponse>> =
        flow {
            emit(RequestStatus.Waiting)

            var response: Response<AuthResponse>? = null
            var errorMessage: String? = null

            try {
                response = consummer.editProfile(userId, body)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Unknown error"
            }

            if (response != null && response.isSuccessful) {
                emit(RequestStatus.Success(response.body()!!))
            } else {
                if (response != null) {
                    emit(
                        RequestStatus.Error(
                            SimplifiedMessage.get(
                                response.errorBody()!!.byteStream().reader().readText()
                            )
                        )
                    )
                }
            }
        }


}

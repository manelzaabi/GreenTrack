package tn.esprit.formtaion.Repository

import kotlinx.coroutines.flow.flow
import tn.esprit.formtaion.Utils.ApiConsummer
import tn.esprit.formtaion.Utils.RequestStatus
import tn.esprit.formtaion.Utils.SimplifiedMessage
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
                        response.errorBody()!!.byteStream().reader().readText()
                    )
                )
            )
        }

    }

    fun registerUser(body: RegisterBody) = flow{
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
}
package tn.esprit.formtaion.model


import com.google.gson.annotations.SerializedName


data class ConsomationResponse(
    @SerializedName("todos")
    val consommation: Consommation
)

data class TotalResponse(
    @SerializedName("total")
    val total: Double
)

data class Consommation(
    @SerializedName("type")
    var type: String?,
    @SerializedName("valeur")
    var valeur: Double?
)

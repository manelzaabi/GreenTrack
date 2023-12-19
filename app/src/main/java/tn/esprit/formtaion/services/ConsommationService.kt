package tn.esprit.formation.fragment

import retrofit2.Call
import retrofit2.http.*


import retrofit2.http.GET
import tn.esprit.formtaion.model.ConsomationResponse
import tn.esprit.formtaion.model.TotalResponse


interface ConsommationService {



    data class ConsommationBody(val type: String, val valeur: Double)


    @POST("/Consom/add")
    fun add(@Body consommationBody: ConsommationBody): Call<ConsomationResponse>

    data class TotalCarbonResponse(val totalCarbon: Double)


        @GET("/Consom/total")
        fun getTotalCarbon(): Call<TotalResponse>

}
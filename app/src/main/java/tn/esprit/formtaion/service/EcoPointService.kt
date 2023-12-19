// EcoPointService.kt (Service)
package tn.esprit.formation.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tn.esprit.formation.model.EcoPoint

interface EcoPointService {
    @POST("/api/eco_point")
    fun addEcoPoint(@Body ecoPoint: EcoPoint): Call<Void>
}

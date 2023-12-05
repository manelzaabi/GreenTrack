package tn.esprit.event_pdm.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.esprit.event_pdm.model.Events

interface EventService {
    @GET("event")
    fun getEvents(): Call<List<Events>>

    @POST("events")
    fun addEvents(@Body event: Events): Call<Events>
}

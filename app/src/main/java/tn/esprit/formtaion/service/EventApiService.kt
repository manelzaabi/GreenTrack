package tn.esprit.formation.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import tn.esprit.formation.models.EventItem
import tn.esprit.formation.models.Events


interface EventApiService {
    @GET("event")
    fun getEvents(): Call<Events>

    @POST("events")
    @Multipart
    fun addEvent(
        @Part image: MultipartBody.Part,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<EventItem>
}


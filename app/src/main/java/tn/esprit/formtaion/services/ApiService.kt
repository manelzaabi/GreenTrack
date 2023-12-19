package tn.esprit.formation.fragment

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {


    const val BASE_URL =  "http://172.16.6.147:3000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }



    val consommationService: ConsommationService by lazy {
        retrofit().create(ConsommationService::class.java)
    }





}
// VotreActivity.kt (ViewModel)
package tn.esprit.formtaion.ViewModel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.formation.model.EcoPoint
import tn.esprit.formation.service.EcoPointService
import tn.esprit.formation.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formation.service.RetrofitClient1
import tn.esprit.formtaion.R

class VotreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ecoPointService = RetrofitClient1.instance.create(EcoPointService::class.java)

        val ecoPointToAdd = EcoPoint(nbrPoint = 1, description = "Description", favoris = true, localisation = "Location")

        // Faites l'appel API
        val call: Call<Void> = ecoPointService.addEcoPoint(ecoPointToAdd)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Traitement réussi
                    // ...
                } else {
                    // Erreur côté serveur
                    // ...
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Erreur de connexion ou autre
                // ...
            }
        })
    }
}

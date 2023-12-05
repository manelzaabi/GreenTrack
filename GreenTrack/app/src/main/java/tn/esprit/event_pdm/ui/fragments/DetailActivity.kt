package tn.esprit.event_pdm.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.event_pdm.model.Events
import tn.esprit.event_pdm.R


import com.squareup.picasso.Picasso
import tn.esprit.event_pdm.model.Event_ID_EXTRA

class DetailActivity : AppCompatActivity() {
    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var joinButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        coverImageView = findViewById(R.id.image)
        titleTextView = findViewById(R.id.title)
        dateTextView = findViewById(R.id.date)
        descriptionTextView = findViewById(R.id.description)
        joinButton = findViewById(R.id.button)

        val eventID = intent.getStringExtra(Event_ID_EXTRA)

        val event = getEventFromDatabase(eventID)

        event?.let {
            Picasso.get().load(it.image).into(coverImageView)
            titleTextView.text = it.title

            dateTextView.text = it.date
            descriptionTextView.text = it.description
            // Ajoutez d'autres mises à jour pour les vues restantes
            // Par exemple : joinButton.text = "Rejoindre l'événement"
        }
    }

    private fun getEventFromDatabase(eventID: String?): Events? {
        // Implémentez la logique pour récupérer l'événement depuis votre base de données en utilisant son ID
        // Vous pouvez utiliser Room, SQLite ou tout autre framework de base de données que vous utilisez
        // Retournez l'événement correspondant à l'ID fourni, ou null s'il n'est pas trouvé
        // Assurez-vous de gérer correctement la logique de récupération depuis votre base de données ici
        return null // Remplacez cela par la logique de récupération de données depuis la base de données
    }
}

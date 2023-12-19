package tn.esprit.formtaion


import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import tn.esprit.formation.models.EventItem
import tn.esprit.formation.models.Event_ID_EXTRA


class DetailActivity : AppCompatActivity() {
    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var detailsTextView: TextView
    private lateinit var joinButton: Button

    val baseUrl = "http://172.20.10.3:3000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        coverImageView = findViewById(R.id.image)
        titleTextView = findViewById(R.id.title)
        dateTextView = findViewById(R.id.date)
        descriptionTextView = findViewById(R.id.description)
        detailsTextView = findViewById(R.id.details)
        joinButton = findViewById(R.id.button)

        val event = intent.getParcelableExtra<EventItem>(Event_ID_EXTRA)


        event?.let {
            val imagePath = baseUrl+it.image
            Picasso.get().load(imagePath).resize(800,600).centerCrop().into(coverImageView)
            titleTextView.text = it.title

            dateTextView.text = it.date
            descriptionTextView.text = it.description
            detailsTextView.text=it.details

        }
    }


}

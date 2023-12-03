package tn.esprit.event_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.event_pdm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventID = intent.getIntExtra(Event_ID_EXTRA, -1)
        val event = eventFromID(eventID)
        if(event != null)
        {
            binding.cover.setImageResource(event.cover)
            binding.orgeniser.text = event.orgeniser
            binding.title.text = event.title
            binding.description.text = event.description

        }
    }

    private fun eventFromID(eventID: Int): Event?
    {
        for(event in eventList)
        {
            if(event.id == eventID)
                return event
        }
        return null
    }
}
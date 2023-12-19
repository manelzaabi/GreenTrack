package tn.esprit.formtaion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract.Events
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import tn.esprit.formation.models.EventItem
import tn.esprit.formation.models.Event_ID_EXTRA
import tn.esprit.formation.repositories.EventRepository
import tn.esprit.formation.service.RetrofitClient
import tn.esprit.formtaion.ViewModel.EventViewModel

import tn.esprit.formtaion.adapters.CardAdapter
import tn.esprit.formtaion.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity(), EventClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var eventViewModel: EventViewModel
    private lateinit var events: Events
    private lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventActivity = this

        val eventService = RetrofitClient.eventService
        val repository = EventRepository(eventService)

        val factory = EventViewModelFactory(repository)
        eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

        val apply = binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)


            adapter = CardAdapter(listOf<EventItem>(), this@EventActivity)
        }

        eventViewModel.getEvents().observe(this, { events ->
            cardAdapter = CardAdapter(events, this@EventActivity)
            binding.recyclerView.adapter = cardAdapter
        })

        val addEventButton = binding.root.findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(applicationContext, Activity_Add::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(events: EventItem) {
        val recyclerView =
            binding.recyclerView
        val layoutManager = recyclerView.layoutManager as? GridLayoutManager
        val position = layoutManager?.findFirstVisibleItemPosition() ?: 0


        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(Event_ID_EXTRA, events)
        startActivity(intent)
    }

}




package tn.esprit.event_pdm.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.Activity_Add
import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.R

import tn.esprit.event_pdm.databinding.ActivityEventBinding
import tn.esprit.event_pdm.ui.adapters.CardAdapter
import tn.esprit.event_pdm.api.RetrofitClient
import tn.esprit.event_pdm.model.Event_ID_EXTRA
import tn.esprit.event_pdm.model.Events

class EventFragment : AppCompatActivity(), EventClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var eventAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchEventsFromAPI()

        val addEventButton = binding.root.findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(applicationContext, Activity_Add::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(event: Events) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(Event_ID_EXTRA, event._id)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            eventAdapter = CardAdapter(emptyList(), this@EventFragment)
            adapter = eventAdapter
        }
    }

    private fun fetchEventsFromAPI() {
        val eventService = RetrofitClient.getEventService()
        val call: Call<List<Events>> = eventService.getEvents()

        call.enqueue(object : Callback<List<Events>> {
            override fun onResponse(call: Call<List<Events>>, response: Response<List<Events>>) {
                if (response.isSuccessful) {
                    val events: List<Events>? = response.body()
                    events?.let {
                        eventAdapter.updateEvents(it)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<Events>>, t: Throwable) {

            }
        })
    }
}

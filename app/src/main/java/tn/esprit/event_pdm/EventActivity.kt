package tn.esprit.event_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import tn.esprit.event_pdm.databinding.ActivityEventBinding
import tn.esprit.event_pdm.databinding.ActivityMainBinding

class EventActivity : AppCompatActivity(), EventClickListener {
    private lateinit var binding: ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateEvents()

        val eventActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = CardAdapter(eventList, eventActivity)
        }
        val addEventButton = binding.root.findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(applicationContext, Activity_Add::class.java)
            startActivity(intent)
        }
    }
    override fun onClick(event: Event)
    {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(Event_ID_EXTRA, event.id)
        startActivity(intent)
    }

    private fun populateEvents()
    {
        val event1 = Event(
            R.drawable.rectangle_56,
            "Victoria Devine",
            "TIMELESS MIND",
            "23/11/2023",
            true,
            "The definitive text on the healing powers of the mind/body connection. In Ageless Body, Timeless Mind, world-renowned pioneer of integrative medicine Deepak Chopra goes beyond ancient mind/body wisdom and current anti-ageing research to show that you do not have to grow old. With the passage of time, you can retain your physical vitality, creativity, memory and self-esteem. Based on the theories of Ayurveda and groundbreaking research, Chopra reveals how we can use our innate capacity for balance to direct the way our bodies metabolize time and achieve our unbounded potential."
        )
        eventList.add(event1)

        val event2 = Event(
            R.drawable.rectangle_56,
            "Amanda Lohrey",
            "THE MIRACLE",
            "23/11/2023",
            true,
            "This is the definitive Event on mindfulness from the beloved Zen master and Nobel Peace Prize nominee Thich Nhat Hanh. With his signature clarity and warmth, he shares practical exercises and anecdotes to help us arrive at greater self-understanding and peacefulness, whether we are beginners or advanced students.\n" + "\n" + "Beautifully written, The Miracle of Mindfulness is the essential guide to welcoming presence in your life and truly living in the moment from the father of mindfulness.\n"
        )
        eventList.add(event2)

        val event3 = Event(
            R.drawable.rectangle_56,
            "M. Scott Peck",
            "ROAD LESS ",
            "23/11/2023",
            true,
            "A timeless classic in personal development, The Road Less Travelled is a landmark work that has inspired millions. Drawing on the experiences of his career as a psychiatrist, Scott Peck combines scientific and spiritual views to guide us through the difficult, painful times in life by showing us how to confront our problems through the key principles of discipline, love and grace.Teaching us how to distinguish dependency from love, how to become a more sensitive parent and how to connect with your true self, this incredible book is the key to accepting and overcoming life's challenges and achieving a higher level of self-understanding."
        )
        eventList.add(event3)

        val event4 = Event(
            R.drawable.rectangle_56,
            "Colleen Hoover",
            "ENDS WITH US",
            "23/11/2023",
            true,
            "'A brave and heartbreaking novel that digs its claws into you and doesn't let go, long after you've finished it' Anna Todd, author of the After series\n" + "\n" + "'A glorious and touching read, a forever keeper' USA Today\n" + "\n" + "'Will break your heart while filling you with hope' Sarah Pekkanen, Perfect Neighbors\n",
        )
        eventList.add(event4)

        val event5 = Event(
            R.drawable.rectangle_56,
            "Ross Coulthart",
            "IN PLAIN",
            "23/11/2023",
            true,
            "Investigative journalist Ross Coulthart has been intrigued by UFOs since mysterious glowing lights were reported near New Zealand's Kaikoura mountains when he was a teenager. The 1978 sighting is just one of thousands since the 1940s, and yet research into UFOs is still seen as the realm of crackpots and conspiracy theorists."
        )
        eventList.add(event5)

        val event6 = Event(
            R.drawable.rectangle_56,
            "Richard Osman",
            "THE CLUB",
            "23/11/2023",
            true,
            "In a peaceful retirement village, four unlikely friends meet up once a week to investigate unsolved murders.\n" + "\n" + "But when a brutal killing takes place on their very doorstep, the Thursday Murder Club find themselves in the middle of their first live case.\n" + "\n" + "Elizabeth, Joyce, Ibrahim and Ron might be pushing eighty but they still have a few tricks up their sleeves.",
        )
        eventList.add(event6)

        val event7 = Event(
            R.drawable.rectangle_56,
            "Michael Robotham",
            "Green day",
            "23/11/2023",
            true,
            "Philomena McCarthy has defied the odds and become a promising young officer with the Metropolitan Police despite being the daughter of a notorious London gangster. Called to the scene of a domestic assault one day, she rescues a bloodied young woman, Tempe Brown, the mistress of a decorated detective. The incident is hushed up, but Phil has unwittingly made a dangerous enemy with powerful friends.\n"
        )
        eventList.add(event7)


        eventList.add(event1)
        eventList.add(event2)
        eventList.add(event3)
        eventList.add(event4)
        eventList.add(event5)
        eventList.add(event6)
        eventList.add(event7)
    }


}
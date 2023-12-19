package tn.esprit.formtaion.ViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import tn.esprit.formation.models.EventItem
import tn.esprit.formation.repositories.EventRepository


class EventViewModel(private val repository: EventRepository) : ViewModel() {

    fun getEvents(): LiveData<List<EventItem>> {
        return repository.getAllEvents()
    }

    fun addEvent(event: EventItem, uri: Uri, context:Context): LiveData<Boolean> {
        return repository.addEvent(event,uri,context)
    }
}


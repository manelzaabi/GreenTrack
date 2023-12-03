package tn.esprit.event_pdm

import java.util.Date


var eventList = mutableListOf<Event>()

val Event_ID_EXTRA = "eventExtra"

class Event(
    var cover: Int,
    var orgeniser: String,
    var title: String,
    var date: String,
    var isfree: Boolean,
    var description: String,
    val id: Int? = eventList.size
)




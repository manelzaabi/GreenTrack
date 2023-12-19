package tn.esprit.formtaion

import tn.esprit.formation.models.EventItem


interface EventClickListener {
    fun onClick(events: EventItem)
}

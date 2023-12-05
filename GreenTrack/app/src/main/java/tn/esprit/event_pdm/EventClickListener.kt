package tn.esprit.event_pdm


import tn.esprit.event_pdm.model.Events


interface EventClickListener
{
    fun onClick(event: Events)
}
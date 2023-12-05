package tn.esprit.event_pdm.ui.adapters
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup

import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.databinding.CardCellBinding
import tn.esprit.event_pdm.model.Events

class CardAdapter(

    private var events: List<Events>,
    private val clickListener: EventClickListener
   )
      : RecyclerView.Adapter<CardViewHolder>()
  {
      fun updateEvents(newEvents: List<Events>) {
          events = newEvents
          notifyDataSetChanged()
      }
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
      {
          val from = LayoutInflater.from(parent.context)
          val binding = CardCellBinding.inflate(from, parent, false)
          return CardViewHolder(binding, clickListener)
      }

      override fun onBindViewHolder(holder: CardViewHolder, position: Int)
      {
          holder.bindEvent(events[position])
      }

      override fun getItemCount(): Int = events.size
  }








package tn.esprit.event_pdm
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import tn.esprit.event_pdm.databinding.CardCellBinding

class CardAdapter(
    private var events: List<Event>,
    private val clickListener: EventClickListener
   )
      : RecyclerView.Adapter<CardViewHolder>()
  {
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








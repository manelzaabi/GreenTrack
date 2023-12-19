package tn.esprit.formtaion.adapters



    import androidx.recyclerview.widget.RecyclerView

    import android.view.LayoutInflater
    import android.view.ViewGroup
    import tn.esprit.formation.models.EventItem
    import tn.esprit.formtaion.CardViewHolder
    import tn.esprit.formtaion.EventClickListener
    import tn.esprit.formtaion.databinding.CardCellBinding


class CardAdapter(

        private var events: List<EventItem>,
        private val clickListener: EventClickListener
    ) : RecyclerView.Adapter<CardViewHolder>() {

        fun updateEvents(newEvents: List<EventItem>) {
            events = newEvents
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val from = LayoutInflater.from(parent.context)
            val binding = CardCellBinding.inflate(from, parent, false)
            return CardViewHolder(binding, clickListener)
        }

        override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
            val event = events[position]
            holder.bindEvent(event)
            holder.itemView.setOnClickListener { clickListener.onClick(event) }
        }


        override fun getItemCount(): Int = events.size
    }


package tn.esprit.event_pdm.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.databinding.CardCellBinding
import tn.esprit.event_pdm.model.Events
import com.squareup.picasso.Picasso


class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: EventClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindEvent(event: Events) {
        Picasso.get().load(event.image).into(cardCellBinding.image)
        cardCellBinding.title.text = event.title

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(event)
        }
    }
}

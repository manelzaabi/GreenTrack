package tn.esprit.event_pdm


import androidx.recyclerview.widget.RecyclerView
import tn.esprit.event_pdm.databinding.CardCellBinding

class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: EventClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindEvent(event: Event)
    {
        cardCellBinding.cover.setImageResource(event.cover)
        cardCellBinding.title.text = event.title
        cardCellBinding.orgeniser.text = event.orgeniser

        cardCellBinding.cardView.setOnClickListener{
            clickListener.onClick(event)
        }
    }
}
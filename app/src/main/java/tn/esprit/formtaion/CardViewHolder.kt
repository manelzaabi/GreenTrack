package tn.esprit.formtaion

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.esprit.formation.models.EventItem
import tn.esprit.formtaion.databinding.CardCellBinding


class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: EventClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{

    val baseUrl = "http://172.20.10.3:3000"
    fun bindEvent(events: EventItem) {
    val imagePath = baseUrl+events.image
        Picasso.get().load(imagePath).resize(800,600).centerCrop().into(cardCellBinding.image)
        cardCellBinding.title.text = events.title

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(events)
        }
    }
}

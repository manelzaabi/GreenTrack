package tn.esprit.formation.models

import android.os.Parcel
import android.os.Parcelable

const val Event_ID_EXTRA = "EVENT_ID_EXTRA"
var eventList = mutableListOf<EventItem>()
data class EventItem(
    var date: String?,
    var description: String?,
    var details: String?,
    var id: String?,
    var image: String?,
    var isFree: Boolean,
    var location: String?,
    //var organisateurs: Array<String>,
   // var participants: List<Any>,
    var price: String?,
    var title: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        //[],
      //  parcel.readParcelableList(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeString(details)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeByte(if (isFree) 1 else 0)
        parcel.writeString(location)
        parcel.writeString(price)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventItem> {
        override fun createFromParcel(parcel: Parcel): EventItem {
            return EventItem(parcel)
        }

        override fun newArray(size: Int): Array<EventItem?> {
            return arrayOfNulls(size)
        }
    }
}
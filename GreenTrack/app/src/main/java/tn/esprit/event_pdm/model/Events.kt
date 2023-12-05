package tn.esprit.event_pdm.model
import android.os.Parcel
import android.os.Parcelable
var eventList = mutableListOf<Events>()

val Event_ID_EXTRA = "eventExtra"
data class Events(
    val _id: String?,
    var image: String?,
    var title: String?,
    var description: String?,
    var date: String?,
    var location: String?,
    var isFree: Boolean,
    var participants: List<String>?,
    var organisateurs: List<String>?,
    var details: String?,
    var price: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(image)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(location)
        parcel.writeByte(if (isFree) 1 else 0)
        parcel.writeStringList(participants)
        parcel.writeStringList(organisateurs)
        parcel.writeString(details)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Events> {
        override fun createFromParcel(parcel: Parcel): Events {
            return Events(parcel)
        }

        override fun newArray(size: Int): Array<Events?> {
            return arrayOfNulls(size)
        }
    }
}
